package com.harol.newsfeed.ui.screens.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.harol.newsfeed.NewsFeedApp
import com.harol.newsfeed.data.enums.ArticleCategory
import com.harol.newsfeed.data.sealed.ApiResult
import com.harol.newsfeed.models.api.NewsResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {
    private val newsRepository = getApplication<NewsFeedApp>().newsRepository

    /// State
    ///

    private val _isLoading = MutableStateFlow(false)
    private val _isError = MutableStateFlow(false)
    private val _newsResponse = MutableStateFlow(NewsResponse())
    private val _selectedCategory: MutableStateFlow<ArticleCategory> =
        MutableStateFlow(ArticleCategory.BUSINESS)

    /// Getters
    ///

    val isLoading: StateFlow<Boolean> get() = _isLoading
    val isError: StateFlow<Boolean> get() = _isError
    val newsResponse: StateFlow<NewsResponse> get() = _newsResponse
    val selectedCategory: StateFlow<ArticleCategory?> get() = _selectedCategory

    /// Error Handler
    ///

    private var errorHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable is Exception) {
            _isError.value = true
        }
    }

    /// Public Functions
    ///
    fun getNewsByCategory(
        category: ArticleCategory = _selectedCategory.value
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = newsRepository.getNewsByCategory(category.categoryName)) {
                is ApiResult.Success -> {
                    result.data?.let { data ->
                        _newsResponse.value = data
                    }
                }
                is ApiResult.Error -> {
                    // TODO(Anyone): Do something with this Error
                }
            }
            _selectedCategory.value = category
            _isLoading.value = false
        }
    }
}