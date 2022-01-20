package com.harol.newsfeed.ui.screens.topNews

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.harol.newsfeed.NewsFeedApp
import com.harol.newsfeed.data.sealed.ApiResult
import com.harol.newsfeed.models.api.NewsResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TopNewsViewModel(application: Application) : AndroidViewModel(application) {

    private val newsRepository = getApplication<NewsFeedApp>().newsRepository

    /// State
    ///

    private val _isLoading = MutableStateFlow(false)
    private val _isError = MutableStateFlow(false)
    private val _newsResponse = MutableStateFlow(NewsResponse())

    /// Getters
    ///

    val isLoading: StateFlow<Boolean> get() = _isLoading
    val isError: StateFlow<Boolean> get() = _isError
    val newsResponse: StateFlow<NewsResponse> get() = _newsResponse

    /// Error Handler
    ///

    private var errorHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable is Exception) {
            _isError.value = true
        }
    }

    /// Public Functions
    ///


    fun getTopNews() {
        if (newsResponse.value.articles != null) {
            return
        }
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = newsRepository.getTopNews()) {
                is ApiResult.Success -> {
                    result.data?.let { data ->
                        _newsResponse.value = data
                    }
                }
                is ApiResult.Error -> {
                    // TODO(Anyone): Do something with this Error
                }
            }
            _isLoading.value = false
        }
    }


    fun getNewsByKeyword(query: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = newsRepository.getNewsByKeyword(query)) {
                is ApiResult.Success -> {
                    result.data?.let { data ->
                        _newsResponse.value = data
                    }
                }
                is ApiResult.Error -> {
                    // TODO(Anyone): Do something with this Error
                }
            }
            _isLoading.value = false
        }
    }
}