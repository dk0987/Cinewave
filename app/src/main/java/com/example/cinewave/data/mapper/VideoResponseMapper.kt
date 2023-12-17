package com.example.cinewave.data.mapper

import androidx.compose.ui.text.substring
import com.example.cinewave.data.dto.response.Video
import com.example.cinewave.data.dto.response.VideoFile
import com.example.cinewave.data.dto.response.VideoResponse
import com.example.cinewave.domain.modal.VideoData
import java.sql.Timestamp

fun Video.toVideoData() : VideoData {
    return VideoData(
        id = id ?: System.currentTimeMillis().toInt() ,
        mediaUri = video_files?.get(0)?.link ?: "",
        previewImageUri = image ?: "",
        username = user?.name ?: "",
        title = url?.replace("https://www.pexels.com/video/" , "") ?: ""
    )
}