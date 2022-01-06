package com.harol.newsfeed.data

class NewsRepository(private val newsManager: NewsManager) {

    suspend fun getTopNews() = newsManager.getTopNews("us")

    suspend fun getNewsByCategory(category: String) =
        newsManager.getNewsByCategory(category)

    suspend fun getNewsBySource(source: String) = newsManager.getNewsBySource(source)

    suspend fun getNewsByKeyword(query: String) = newsManager.getNewsByKeyword(query)

}