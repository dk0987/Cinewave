package com.example.cinewave.domain.modal

import android.media.browse.MediaBrowser
import androidx.media3.common.MediaItem
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.source.MediaSource

data class VideoData(
    val id : Int,
    val mediaUri : String,
    val previewImageUri : String,
    val username : String ,
    val title : String
)


@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun VideoData.toMediaItem() : MediaItem{
    return MediaItem.fromUri(mediaUri)
}