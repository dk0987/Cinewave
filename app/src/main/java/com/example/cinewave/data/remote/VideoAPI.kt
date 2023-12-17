package com.example.cinewave.data.remote

import com.example.cinewave.data.dto.response.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoAPI {

    @GET("/videos/search")
    suspend fun getVideos(
        @Query("per_page") per_page : Int = 10,
        @Query("page") page : Int = 800 ,
        @Query("query") query : String = "vertical",
    ) : VideoResponse

}