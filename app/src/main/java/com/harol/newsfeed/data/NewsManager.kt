package com.harol.newsfeed.data

import com.harol.newsfeed.models.NewsResponse
import com.harol.newsfeed.network.NewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NewsManager(private val newsApiService: NewsApiService) {


    suspend fun getArticles(country: String): NewsResponse = withContext(Dispatchers.IO) {
        newsApiService.getTopArticles(
            country,
            "d2691289ff474bb9850b71fa026ce470"
        )
    }

    suspend fun getArticlesBySource(source: String): NewsResponse =
        withContext(Dispatchers.IO) {
            newsApiService.searchArticlesBySource(
                source = source,
                "d2691289ff474bb9850b71fa026ce470"
            )
        }

    suspend fun getSearchedArticles(query: String): NewsResponse =
        withContext(Dispatchers.IO) {
            newsApiService.searchArticles(
                query,
                "d2691289ff474bb9850b71fa026ce470"
            )
        }

    suspend fun getArticlesByCategory(category: String): NewsResponse =
        withContext(Dispatchers.IO) {
            newsApiService.getCategories(
                category,
                "d2691289ff474bb9850b71fa026ce470"
            )
        }
}