package com.harol.newsfeed.network

import com.harol.newsfeed.models.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NewsApiManager(private val newsApiService: NewsApiService) {

    suspend fun getTopNews(country: String): NewsResponse = withContext(Dispatchers.IO) {
        newsApiService.getTopNews(
            country,
        )
    }

    suspend fun getNewsBySource(source: String): NewsResponse =
        withContext(Dispatchers.IO) {
            newsApiService.getNewsBySource(
                source = source,
            )
        }

    suspend fun getNewsByKeyword(query: String): NewsResponse =
        withContext(Dispatchers.IO) {
            newsApiService.getNewsByKeyword(
                query,
            )
        }

    suspend fun getNewsByCategory(category: String): NewsResponse =
        withContext(Dispatchers.IO) {
            newsApiService.getNewsByCategory(
                category,
            )
        }
}