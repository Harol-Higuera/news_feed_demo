package com.harol.newsfeed.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.harol.newsfeed.NewsFeedApp
import com.harol.newsfeed.models.NewsResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val newsRepository = getApplication<NewsFeedApp>().newsRepository

    /// Loading Indicator
    ///
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading


    /// Error View
    ///
    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> get() = _isError
    private var errorHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable is Exception) {
            _isError.value = true
        }
    }

    /// :::::: News Response ::::::
    ///
    private val _newsResponse = MutableStateFlow(NewsResponse())
    val newsResponse: StateFlow<NewsResponse> get() = _newsResponse

    fun getTopArticles() {
        _isLoading.value = true
        // Note: We can safely run the Coroutine repository.getArticles() in a
        // viewModelScope launch
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            _newsResponse.value = newsRepository.getTopNews()
            _isLoading.value = false
        }
    }



    /// :::::: Search Feature::::::
    ///
    val query = MutableStateFlow("")
    private val _searchNewsResponse = MutableStateFlow(NewsResponse())
    val searchNewsResponse: StateFlow<NewsResponse> get() = _searchNewsResponse

    fun getSearchArticles(query: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            _searchNewsResponse.value = newsRepository.getNewsByKeyword(query)
            _isLoading.value = false
        }
    }
}