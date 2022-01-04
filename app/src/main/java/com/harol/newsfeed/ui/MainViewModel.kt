package com.harol.newsfeed.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.harol.newsfeed.NewsFeedApp
import com.harol.newsfeed.data.enums.ArticleCategory
import com.harol.newsfeed.data.enums.toArticleCategory
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
            _newsResponse.value = newsRepository.getArticles()
            _isLoading.value = false
        }
    }

    /// :::::: Articles By Category ::::::
    ///
    private val _getArticleByCategory = MutableStateFlow(NewsResponse())
    val getArticleByCategory: StateFlow<NewsResponse> get() = _getArticleByCategory

    fun getArticlesByCategory(category: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            _getArticleByCategory.value = newsRepository.getArticlesByCategory(category)
            _isLoading.value = false
        }
    }


    /// :::::: Selected Category ::::::
    ///
    private val _selectedCategory: MutableStateFlow<ArticleCategory?> = MutableStateFlow(null)
    val selectedCategory: StateFlow<ArticleCategory?> get() = _selectedCategory

    fun onSelectedCategory(category: String) {
        _selectedCategory.value = category.toArticleCategory()
    }

    /// :::::: Source Name ::::::
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


    /// :::::: Search Feature::::::
    ///
    val query = MutableStateFlow("")
    private val _searchNewsResponse = MutableStateFlow(NewsResponse())
    val searchNewsResponse: StateFlow<NewsResponse> get() = _searchNewsResponse

    fun getSearchArticles(query: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            _searchNewsResponse.value = newsRepository.getSearchArticles(query)
            _isLoading.value = false
        }
    }
}