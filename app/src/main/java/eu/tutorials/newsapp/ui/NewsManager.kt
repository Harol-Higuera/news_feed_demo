package eu.tutorials.newsapp.ui

import eu.tutorials.newsapp.model.NewsResponse
import eu.tutorials.newsapp.network.NewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NewsManager(private val service: NewsService) {


    suspend fun getArticles(country: String): NewsResponse = withContext(Dispatchers.IO) {
        service.getTopArticles(
            country,
            "d2691289ff474bb9850b71fa026ce470"
        )
    }

    suspend fun getArticlesBySource(source: String): NewsResponse =
        withContext(Dispatchers.IO) {
            service.searchArticlesBySource(
                source = source,
                "d2691289ff474bb9850b71fa026ce470"
            )
        }

    suspend fun getSearchedArticles(query: String): NewsResponse =
        withContext(Dispatchers.IO) {
            service.searchArticles(
                query,
                "d2691289ff474bb9850b71fa026ce470"
            )
        }

    suspend fun getArticlesByCategory(category: String): NewsResponse =
        withContext(Dispatchers.IO) {
            service.getCategories(
                category,
                "d2691289ff474bb9850b71fa026ce470"
            )
        }
}