package com.nassiansoft.watchit.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nassiansoft.watchit.R
import com.nassiansoft.watchit.data.model.Movie
import com.nassiansoft.watchit.databinding.SearchViewHolderBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class SearchAdapter @Inject constructor() : RecyclerView.Adapter<SearchAdapter.Vh>() {

    var data = emptyList<Movie>()

    private val _addClickListener=PublishSubject.create<Movie>()
    fun getAddClickListener(): Observable<Movie> =_addClickListener.hide()

    private val _detailClickListener=PublishSubject.create<Movie>()
    fun getDetailClickListener(): Observable<Movie> =_detailClickListener

    inner class Vh(val binding: SearchViewHolderBinding)
        : RecyclerView.ViewHolder(binding.root){
        init {
            binding.addImageView.setOnClickListener {
                binding.movie?.let {
                    _addClickListener.onNext(it)
                }
            }

            binding.root.setOnClickListener {
                binding.movie?.let {
                    _detailClickListener.onNext(it)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        val inflater=LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SearchViewHolderBinding>(
            inflater,
            R.layout.search_view_holder,
            parent,
            false
        )

        return Vh(binding)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val item=data[position]
        holder.binding.movie=item
        holder.binding.addImageView.isEnabled=!item.inMyList
        holder.binding.addImageView.alpha=  if (item.inMyList) 0.5f else 1.0f
    }

    override fun getItemCount(): Int = data.size

}
