package com.harol.newsfeed.ui.screens.sources

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

class SourcesViewModel(application: Application) : AndroidViewModel(application) {
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

    /// :::::: Article By Source ::::::
    ///
    val sourceName = MutableStateFlow("engadget")
    private val _getArticleBySource = MutableStateFlow(NewsResponse())
    val getArticleBySource: StateFlow<NewsResponse> get() = _getArticleBySource

    fun getArticlesBySource() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            _getArticleBySource.value = newsRepository.getArticlesBySource(sourceName.value)
            _isLoading.value = false
        }
    }

}