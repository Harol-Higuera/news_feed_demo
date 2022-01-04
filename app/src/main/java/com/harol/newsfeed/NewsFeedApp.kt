package com.harol.newsfeed

import android.app.Application
import com.harol.newsfeed.data.NewsManager
import com.harol.newsfeed.data.NewsRepository
import com.harol.newsfeed.network.NewsApiClient

class NewsFeedApp : Application() {

    private val manager by lazy {
        NewsManager(NewsApiClient.RETROFIT_API_SERVICE)
    }

    val newsRepository by lazy {
        NewsRepository(manager)
    }
}