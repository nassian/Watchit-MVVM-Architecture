package com.nassiansoft.watchit.ui.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nassiansoft.watchit.R
import com.nassiansoft.watchit.databinding.HomeFragmentBinding
import com.nassiansoft.watchit.show
import com.nassiansoft.watchit.showSnack
import com.nassiansoft.watchit.ui.ViewModelFactory
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val TAG = "HomeFragment"

class HomeFragment : DaggerFragment() {


    @Inject
    lateinit var factory: ViewModelFactory<HomeViewModel>

    @Inject
    lateinit var mAdapter: HomeAdapter

    private val viewModel by viewModels<HomeViewModel> { factory }
    private lateinit var binding: HomeFragmentBinding

    private val compositeDisposable by lazy { CompositeDisposable() }

    private var animationShowed=false


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        binding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_searchFragment)
        }

        setupRecyclerView()
        observeMovieList()
        if (savedInstanceState!=null) animationShowed=true

       return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


    override fun onStart() {
        super.onStart()
        binding.splashLayout.show(!animationShowed)
    }





    private fun setupRecyclerView() {
        binding.homeRecyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
        itemCheckedListener()
        itemClickedListener()
    }

    private fun observeMovieList() {
        viewModel.getMyMovies().observe(viewLifecycleOwner) {
            animate()
            it?.let {
                mAdapter.submitList(it)
            }
        }
    }

    private fun itemCheckedListener() {

        compositeDisposable.add(
                mAdapter.getWatchListener().flatMapCompletable {
                    viewModel.markMovieAsWatched(it)
                }.subscribe({
                    Log.d(TAG, "itemCheckedListener: item has been updated")
                }, { e -> e.message?.let { binding.root.showSnack(it) } })

        )
    }

    private fun itemClickedListener() {
        compositeDisposable.add(
                mAdapter.getDetailListener().subscribe(
                        {
                            Log.d(TAG, "itemClickedListener: $it")
                            findNavController().navigate(
                                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                            )
                        }, { Log.d(TAG, "itemClickedListenerError: ${it.message}") }
                )
        )
    }

    private fun animate(){

        val onEnd2={
            binding.splashLayout.show(false)
            animationShowed=true
        }
        val onEnd1={
            scaler(100f,onEnd2)
        }

        scaler(0.7f,onEnd1)

    }

    private fun scaler(ratio:Float,onEnd:()->Unit){

        val scaleX=PropertyValuesHolder.ofFloat(View.SCALE_X,ratio)
        val scaleY=PropertyValuesHolder.ofFloat(View.SCALE_Y,ratio)

        val objectAnimator=ObjectAnimator.ofPropertyValuesHolder(binding.splashImageView,
            scaleX,scaleY)

        objectAnimator.apply {
            objectAnimator.addListener(object :AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                   // binding.splashLayout.show(false)
                    onEnd()
                }
            })
            start()
        }

    }


}