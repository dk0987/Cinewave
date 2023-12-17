package com.example.cinewave.util

interface Pagination<T> {

    suspend fun loadItems()

}