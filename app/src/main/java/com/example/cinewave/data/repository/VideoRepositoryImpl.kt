package com.example.cinewave.data.repository

import com.example.cinewave.data.mapper.toVideoData
import com.example.cinewave.data.remote.VideoAPI
import com.example.cinewave.domain.modal.VideoData
import com.example.cinewave.domain.repository.VideoRepository
import com.example.cinewave.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val api : VideoAPI
) : VideoRepository {

    override suspend fun getVideos(
        per_page: Int,
        page: Int,
        query: String
    ): Resource<List<VideoData>> {
        return try {
            val response = api.getVideos(per_page, page, query)
            if (response.videos != null){
                Resource.Successful(response.videos.map { it.toVideoData() })
            }
            else Resource.Error("Something Went Wrong")
        }catch (e : IOException){
            Resource.Error("Something went wrong")
        }catch (e : HttpException){
            Resource.Error("No Internet Connection")
        }
    }
}