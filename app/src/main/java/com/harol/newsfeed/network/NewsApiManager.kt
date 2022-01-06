package com.harol.newsfeed.network

import com.harol.newsfeed.models.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NewsApiManager(private val newsApiService: NewsApiService) {

    suspend fun getTopNews(country: String): NewsResponse = withContext(Dispatchers.IO) {
        newsApiService.getTopNews(
            country,
            "715861e4b4874400a164e9d7bca63c0c"
        )
    }

    suspend fun getNewsBySource(source: String): NewsResponse =
        withContext(Dispatchers.IO) {
            newsApiService.getNewsBySource(
                source = source,
                "715861e4b4874400a164e9d7bca63c0c"
            )
        }

    suspend fun getNewsByKeyword(query: String): NewsResponse =
        withContext(Dispatchers.IO) {
            newsApiService.getNewsByKeyword(
                query,
                "715861e4b4874400a164e9d7bca63c0c"
            )
        }

    suspend fun getNewsByCategory(category: String): NewsResponse =
        withContext(Dispatchers.IO) {
            newsApiService.getNewsByCategory(
                category,
                "715861e4b4874400a164e9d7bca63c0c"
            )
        }
}