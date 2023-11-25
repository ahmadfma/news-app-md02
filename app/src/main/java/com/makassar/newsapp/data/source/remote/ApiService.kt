package com.makassar.newsapp.data.source.remote

import com.makassar.newsapp.data.source.remote.response.GetNewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = "b20c0ec3d33f4ed599f61e82a2a7484e"
    ): Response<GetNewsResponse>

}