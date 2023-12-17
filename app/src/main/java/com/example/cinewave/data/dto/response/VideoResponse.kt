package com.example.cinewave.data.dto.response

data class VideoResponse(
    val page: Int?,
    val per_page: Int?,
    val prev_page: String?,
    val total_results: Int?,
    val url: String?,
    val videos: List<Video>?
)