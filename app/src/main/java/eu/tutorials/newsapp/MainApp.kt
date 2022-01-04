package eu.tutorials.newsapp

import android.app.Application
import eu.tutorials.newsapp.data.NewsManager
import eu.tutorials.newsapp.data.NewsRepository
import eu.tutorials.newsapp.network.NewsApiClient

class MainApp : Application() {

    private val manager by lazy {
        NewsManager(NewsApiClient.RETROFIT_API_SERVICE)
    }

    val newsRepository by lazy {
        NewsRepository(manager)
    }
}