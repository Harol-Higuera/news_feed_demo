package com.harol.newsfeed.network

import com.harol.newsfeed.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getTopNews(
        @Query("country") country: String,
    ): NewsResponse

    @GET("top-headlines")
    suspend fun getNewsByCategory(
        @Query("category") category: String,
    ): NewsResponse

    @GET("everything")
    suspend fun getNewsByKeyword(
        @Query("q") query: String,
    ): NewsResponse

    @GET("everything")
    suspend fun getNewsBySource(
        @Query("sources") source: String,
    ): NewsResponse
}