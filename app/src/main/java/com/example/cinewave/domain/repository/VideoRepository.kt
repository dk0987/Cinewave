package com.example.cinewave.domain.repository

import com.example.cinewave.domain.modal.VideoData
import com.example.cinewave.util.Resource

interface VideoRepository {

    suspend fun getVideos(
        per_page : Int = 10,
        page : Int = 40,
        query : String = "Vertical"
    ) : Resource<List<VideoData>>

}