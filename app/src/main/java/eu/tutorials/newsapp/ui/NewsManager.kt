package eu.tutorials.newsapp.ui

import eu.tutorials.newsapp.models.NewsResponse
import eu.tutorials.newsapp.network.NewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NewsManager(private val apiService: NewsApiService) {


    suspend fun getArticles(country: String): NewsResponse = withContext(Dispatchers.IO) {
        apiService.getTopArticles(
            country,
            "d2691289ff474bb9850b71fa026ce470"
        )
    }

    suspend fun getArticlesBySource(source: String): NewsResponse =
        withContext(Dispatchers.IO) {
            apiService.searchArticlesBySource(
                source = source,
                "d2691289ff474bb9850b71fa026ce470"
            )
        }

    suspend fun getSearchedArticles(query: String): NewsResponse =
        withContext(Dispatchers.IO) {
            apiService.searchArticles(
                query,
                "d2691289ff474bb9850b71fa026ce470"
            )
        }

    suspend fun getArticlesByCategory(category: String): NewsResponse =
        withContext(Dispatchers.IO) {
            apiService.getCategories(
                category,
                "d2691289ff474bb9850b71fa026ce470"
            )
        }
}