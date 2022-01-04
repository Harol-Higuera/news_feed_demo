package eu.tutorials.newsapp.network

import eu.tutorials.newsapp.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {


    @GET("top-headlines")
    suspend fun getTopArticles(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse

    @GET("top-headlines")
    suspend fun getCategories(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse

    @GET("everything")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse

    @GET("everything")
    suspend fun searchArticlesBySource(
        @Query("sources") source: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse
}