package com.nassiansoft.watchit.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nassiansoft.watchit.R
import com.nassiansoft.watchit.data.model.Movie
import com.nassiansoft.watchit.data.network.Resource
import com.nassiansoft.watchit.databinding.SearchFragmentBinding
import com.nassiansoft.watchit.show
import com.nassiansoft.watchit.showSnack
import com.nassiansoft.watchit.ui.ViewModelFactory
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

private const val TAG = "SearchFragment"

class SearchFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelFactory<SearchViewModel>

    @Inject
    lateinit var mAdapter: SearchAdapter

    private val viewModel: SearchViewModel by viewModels { factory }
    private lateinit var binding: SearchFragmentBinding
    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)

        binding.viewModel = viewModel
        setupRecyclerView()
        Log.d(TAG, "onCreateView: $viewModel")
        observeSearchList()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


    private fun setupRecyclerView() {
        binding.searchRecyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
        setAddClickListener()
        setDetailClickListener()
    }

    private fun observeSearchList() {
        viewModel.getSearchList().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> binding.searchProgressBar.show(true)
                is Resource.Error -> {
                    binding.searchProgressBar.show(false)
                    it.message?.let { it1 -> binding.root.showSnack(it1) }
                }
                is Resource.Success -> {
                    binding.searchProgressBar.show(false)
                    it.data?.let { list ->
                        updateRecyclerView(list)
                    }
                }
            }
        }
    }


    private fun updateRecyclerView(list: List<Movie>) {
        mAdapter.data = list
        mAdapter.notifyDataSetChanged()
        (binding.searchRecyclerView.layoutManager as LinearLayoutManager)
                .scrollToPosition(0)
    }

    private fun setAddClickListener() {

        compositeDisposable.add(
                mAdapter.getAddClickListener()
                        .flatMapCompletable { movie ->
                            viewModel.saveMovie(movie)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .doOnComplete {
                                        binding.root.showSnack("${movie.trackName} added to your list")
                                        updateOneItem(movie)
                                    }
                        }.subscribe({}, { e -> binding.root.showSnack("Unable to add this movie") })
        )
    }

    private fun setDetailClickListener() {
        compositeDisposable.add(

                mAdapter.getDetailClickListener().subscribe(

                        {   ///a way to find out the movie detail is opened from search
                            val movie = it.copy(trackId = "-1")
                            findNavController().navigate(SearchFragmentDirections
                                    .actionSearchFragmentToDetailFragment(movie))
                        },
                        { e -> Log.d(TAG, "setDetailClickListener: $e") }
                )
        )
    }


    private fun updateOneItem(movie: Movie) {
        val list = mAdapter.data.toMutableList()
        val index = list.indexOf(movie)
        movie.inMyList = true
        list[index] = movie
        mAdapter.data = list
        mAdapter.notifyItemChanged(index)
    }


}