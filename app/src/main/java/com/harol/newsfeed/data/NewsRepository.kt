package com.harol.newsfeed.data

import com.harol.newsfeed.network.NewsApiManager

class NewsRepository(private val newsApiManager: NewsApiManager) {

    suspend fun getTopNews() = newsApiManager.getTopNews("us")

    suspend fun getNewsByCategory(category: String) =
        newsApiManager.getNewsByCategory(category)

    suspend fun getNewsBySource(source: String) = newsApiManager.getNewsBySource(source)

    suspend fun getNewsByKeyword(query: String) = newsApiManager.getNewsByKeyword(query)

}