package com.example.cinewave.di

import com.example.cinewave.data.remote.VideoAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                val modifiedRequest =it.request().newBuilder()
                    .addHeader("Authorization" , "Eh6qOdN6y5AvoiZbhYo228jMuQoIf9c3KanTTau5CgzsqDvK9ulLOSsX")
                    .addHeader("X-RapidAPI-Key" , "c5b62cb95cmsh47c83abd5fca932p13fc0bjsnba82b5cd9859")
                    .build()
                it.proceed(modifiedRequest)
            }.addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
    }

    @Provides
    @Singleton
    fun provideVideoAPI(okHttpClient: OkHttpClient) : VideoAPI{
        return Retrofit.Builder()
            .baseUrl("https://pexelsdimasv1.p.rapidapi.com/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(VideoAPI::class.java)
    }

}