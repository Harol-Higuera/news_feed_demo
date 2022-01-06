package com.harol.newsfeed

import android.app.Application
import com.harol.newsfeed.data.NewsRepository
import com.harol.newsfeed.network.NewsApiClient
import com.harol.newsfeed.network.NewsApiManager

class NewsFeedApp : Application() {

    private val newsApiManager by lazy {
        NewsApiManager(NewsApiClient.RETROFIT_API_SERVICE)
    }

    val newsRepository by lazy {
        NewsRepository(newsApiManager)
    }
}