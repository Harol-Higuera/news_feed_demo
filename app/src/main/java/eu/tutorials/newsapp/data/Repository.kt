package eu.tutorials.newsapp.data

import eu.tutorials.newsapp.ui.NewsManager

class Repository(val manager: NewsManager) {

    suspend fun getArticles() = manager.getArticles("us")

    suspend fun getArticlesByCategory(category: String) = manager.getArticlesByCategory(category)

    suspend fun getArticlesBySource(source: String) = manager.getArticlesBySource(source)

    suspend fun getSearchArticles(query: String) = manager.getSearchedArticles(query)

}