package eu.tutorials.newsapp

import android.app.Application
import eu.tutorials.newsapp.data.Repository
import eu.tutorials.newsapp.network.Api
import eu.tutorials.newsapp.ui.NewsManager

class MainApp: Application() {

    private val manager by lazy {
        NewsManager(Api.retrofitService)
    }

    val repository by lazy {
        Repository(manager)
    }
}