package com.app.workshop.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getPosts() = apiService.getPosts()
}