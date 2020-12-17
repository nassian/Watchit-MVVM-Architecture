package com.nassiansoft.watchit.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nassiansoft.watchit.R
import com.nassiansoft.watchit.data.model.Movie
import com.nassiansoft.watchit.databinding.HomeViewHolderBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


class HomeAdapter @Inject constructor():ListAdapter<Movie,HomeAdapter.HomeVh> (MovieDiffUtilCallback()) {



    private val _watchClickListener=PublishSubject.create<Movie>()
    fun getWatchListener(): Observable<Movie> =_watchClickListener.hide()

    private val _detailClickListener=PublishSubject.create<Movie>()
    fun getDetailListener(): Observable<Movie> = _detailClickListener

    inner class HomeVh(val binding:HomeViewHolderBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.watchedCheckBox.setOnClickListener{
                binding.movie?.let {

                    it.watched=binding.watchedCheckBox.isChecked
                    _watchClickListener.onNext(it)
                }
            }

            binding.root.setOnClickListener {
                binding.movie?.let {
                    _detailClickListener.onNext(it)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeVh {
        val layoutInflater=LayoutInflater.from(parent.context)
        val binding=DataBindingUtil.inflate<HomeViewHolderBinding>(
            layoutInflater, R.layout.home_view_holder,parent,false)
        return HomeVh(binding)
    }

    override fun onBindViewHolder(holder: HomeVh, position: Int) {
        val item=getItem(position)
        holder.binding.movie=item
    }


}


class MovieDiffUtilCallback:DiffUtil.ItemCallback<Movie> (){

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
       return oldItem.trackId==newItem.trackId
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
      return  oldItem==newItem
    }

}


