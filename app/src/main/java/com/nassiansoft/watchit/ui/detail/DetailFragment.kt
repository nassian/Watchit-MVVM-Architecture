package com.nassiansoft.watchit.ui.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util
import com.nassiansoft.watchit.PlayerHelper
import com.nassiansoft.watchit.R
import com.nassiansoft.watchit.data.model.Movie
import com.nassiansoft.watchit.databinding.DetailFragmentBinding
import com.nassiansoft.watchit.show
import com.nassiansoft.watchit.showSnack
import com.nassiansoft.watchit.ui.ViewModelFactory
import dagger.Lazy
import dagger.android.support.DaggerFragment
import javax.inject.Inject

private const val TAG = "DetailFragment"

class DetailFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelFactory<DetailViewModel>

    private lateinit var binding: DetailFragmentBinding

    private val viewModel: DetailViewModel by viewModels { factory }

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var movie: Movie



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        movie = args.movie
        viewModel.movie = movie
        binding.viewModel = viewModel
        setupDeleteButton()
        viewModel.playerHelper.preparePlayer(movie.previewUrl,binding.playerView)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart: ${viewModel.playerHelper}")
        if(Util.SDK_INT>=24){

            viewModel.playerHelper.preparePlayer(movie.previewUrl,binding.playerView)
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT<24){
            viewModel.playerHelper.preparePlayer(movie.previewUrl,binding.playerView)
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT<24){
            viewModel.playerHelper.releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT>=24){
            viewModel.playerHelper.releasePlayer()
        }
    }




    private fun setupDeleteButton() {
        binding.detailDeleteButton.show(movie.inMyList && movie.trackId != "-1")
        if (movie.inMyList) {

            viewModel.getMovieDeleted().observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigateUp()
                }
            }
        }
    }






}