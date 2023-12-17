package com.example.cinewave.presentation.home

import com.example.cinewave.domain.modal.VideoData

data class PaginationVideo(
    val items : List<VideoData> = emptyList() ,
    val isLoading : Boolean = false ,
    val endReached :  Boolean = false,
)