package com.example.cinewave.presentation.component

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.cinewave.domain.modal.VideoData
import com.example.cinewave.presentation.home.HomeViewModel
import com.example.cinewave.presentation.util.findActivity
import kotlinx.coroutines.flow.distinctUntilChanged
import java.nio.file.Files.list

@OptIn(ExperimentalFoundationApi::class)
@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun VideoPlayer(
    video : VideoData ,
    player : Player?,
    viewModel: HomeViewModel,
    modifier : Modifier
) {
    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifeCycleOwner.lifecycle
    println("Happy ")

    ComposableLifeCycle{ _ , event ->
        when(event){
            Lifecycle.Event.ON_START -> viewModel.createPlayer(context)
            Lifecycle.Event.ON_STOP-> viewModel.releasePlayer(context.findActivity()?.isChangingConfigurations == true)
            else -> {}
        }
    }

    var showPLayer by remember { mutableStateOf(false) }

    if (player != null){
        PlayerListener(player = player){ event ->
            when(event) {
                 Player.EVENT_RENDERED_FIRST_FRAME -> {
                     showPLayer = true
                 }
                Player.EVENT_PLAYER_ERROR -> {

                }
            }
        }

        val playerView = rememberPlayerView(player)
        Player(
            playerView = playerView ,
            viewModel = viewModel
        )
    }
    if (!showPLayer){
        Box(modifier = Modifier.fillMaxSize()){
            Image(
                painter = rememberAsyncImagePainter(video.previewImageUri),
                contentDescription = "Thumbnail" ,
                contentScale = ContentScale.Crop ,
                modifier = Modifier.fillMaxSize()
            )
        }
    }


}

@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun rememberPlayerView(player: Player): PlayerView {
    val context = LocalContext.current
    val playerView = remember {
        PlayerView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
            useController = false
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            setShowBuffering(PlayerView.SHOW_BUFFERING_NEVER)
            this.player = player
        }
    }
    DisposableEffect(key1 = player) {
        playerView.player = player
        onDispose {
            playerView.player = null
        }
    }
    return playerView
}