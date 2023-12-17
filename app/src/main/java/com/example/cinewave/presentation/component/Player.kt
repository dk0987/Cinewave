package com.example.cinewave.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import com.example.cinewave.presentation.home.HomeViewModel

@Composable
fun Player(
    playerView: PlayerView ,
    viewModel: HomeViewModel ,
    modifier : Modifier = Modifier
){
    AndroidView(
        factory = { playerView } ,
        modifier = modifier.fillMaxSize()
    )
}