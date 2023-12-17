package com.example.cinewave.presentation.util

import androidx.media3.common.Player
import com.example.cinewave.domain.modal.VideoData

data class VideoUIState(
    val player : Player? = null,
    val paused : Boolean = false
){
    fun playMediaAt(position : Int){
        player?.let {
            if (it.currentMediaItemIndex == position && it.isPlaying)
                return
            it.playWhenReady = true
            it.seekToDefaultPosition(position)
            it.prepare()
        }
    }

}