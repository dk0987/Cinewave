package com.example.cinewave.presentation.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.cinewave.domain.modal.toMediaItem
import com.example.cinewave.domain.repository.VideoRepository
import com.example.cinewave.presentation.util.VideoUIState
import com.example.cinewave.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository :  VideoRepository
): ViewModel() {

    private val _paginatedVideo = MutableStateFlow(PaginationVideo())
    val paginatedVideo = _paginatedVideo.asStateFlow()

    private val _state = MutableStateFlow(VideoUIState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<Boolean>()
    val effect = _effect.asSharedFlow()



    init {
        viewModelScope.launch {
            when(val response = repository.getVideos(page = Random.nextInt(0 , 200))) {
                is Resource.Successful -> {
                    val items = response.data ?: emptyList()
                    _paginatedVideo.update {
                        it.copy(
                            items = paginatedVideo.value.items + items.shuffled() ,
                        )
                    }

                }
                is Resource.Error -> {

                }
            }
        }

    }

    fun createPlayer(context: Context){
        _state.update { state->
            if (state.player == null) {
                state.copy(player = ExoPlayer.Builder(context).build().apply {
                    repeatMode = Player.REPEAT_MODE_ONE
                    setMediaItems(paginatedVideo.value.items.map { it.toMediaItem() })
                    prepare()
                })
            }
            else
                state
        }
    }

    fun releasePlayer(isChangingConfiguration : Boolean){
        if (isChangingConfiguration)
            return
        state.value.player?.release()
        _state.value = state.value.copy(
            player = null
        )
    }


    fun onTap(){
        viewModelScope.launch {
            state.value.player?.let { player ->
                if (player.isPlaying) {
                    player.pause()
                    _effect.emit(true)
                }
                else {
                    player.play()
                    _effect.emit(false)
                }
            }
        }
    }

}