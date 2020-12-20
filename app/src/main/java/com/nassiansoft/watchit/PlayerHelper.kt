package com.nassiansoft.watchit

import android.app.Application
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import javax.inject.Inject

interface IPlayerHelper {
    fun preparePlayer(url: String?, playerView: PlayerView)
    fun releasePlayer()

}

class PlayerHelper @Inject constructor(private val application: Application) : IPlayerHelper {

    private var playWhenReady=false
    private var position=0L
    private var currentWindow=0
    private var simpleExoPlayer:SimpleExoPlayer?=null

    override fun preparePlayer(url:String?, playerView: PlayerView){
        url?:return

        if (simpleExoPlayer==null){
            simpleExoPlayer= SimpleExoPlayer.Builder(application)
            .build()
        }
        playerView.player=simpleExoPlayer
        simpleExoPlayer?.setMediaItem(MediaItem.fromUri(url))
        simpleExoPlayer?.playWhenReady=playWhenReady
        simpleExoPlayer?.seekTo(currentWindow,position)
        simpleExoPlayer?.prepare()

    }

    override fun releasePlayer(){
        simpleExoPlayer?:return
        position= simpleExoPlayer!!.currentPosition
        currentWindow=simpleExoPlayer!!.currentWindowIndex
        playWhenReady=simpleExoPlayer!!.playWhenReady
        simpleExoPlayer!!.release()
        simpleExoPlayer=null
    }




}