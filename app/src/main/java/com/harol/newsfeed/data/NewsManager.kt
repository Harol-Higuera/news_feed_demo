package com.harol.newsfeed.data

import com.harol.newsfeed.models.NewsResponse
import com.harol.newsfeed.network.NewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NewsManager(private val newsApiService: NewsApiService) {


    suspend fun getArticles(country: String): NewsResponse = withContext(Dispatchers.IO) {
        newsApiService.getTopArticles(
            country,
            "715861e4b4874400a164e9d7bca63c0c"
        )
    }

    suspend fun getArticlesBySource(source: String): NewsResponse =
        withContext(Dispatchers.IO) {
            newsApiService.searchArticlesBySource(
                source = source,
                "715861e4b4874400a164e9d7bca63c0c"
            )
        }

    suspend fun getSearchedArticles(query: String): NewsResponse =
        withContext(Dispatchers.IO) {
            newsApiService.searchArticles(
                query,
                "715861e4b4874400a164e9d7bca63c0c"
            )
        }

    suspend fun getArticlesByCategory(category: String): NewsResponse =
        withContext(Dispatchers.IO) {
            newsApiService.getCategories(
                category,
                "715861e4b4874400a164e9d7bca63c0c"
            )
        }
}