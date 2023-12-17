package com.example.cinewave.util

sealed class Resource<T>(data : T? = null, error : String? = null){
    data class Successful<T>(val data: T?) : Resource<T>(data)
    data class Error<T>(val message : String) : Resource<T>(data = null , message)
}
