package eu.tutorials.newsapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import eu.tutorials.newsapp.data.ArticleCategory
import eu.tutorials.newsapp.data.getArticleCategory
import eu.tutorials.newsapp.model.NewsResponse
import eu.tutorials.newsapp.network.NewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NewsManager(private val service: NewsService) {

    val sourceName = mutableStateOf("abc-news")

    private val _newsResponse =
        mutableStateOf(NewsResponse())
    val newsResponse: MutableState<NewsResponse>
        @Composable get() = remember {
            _newsResponse
        }

    val query = mutableStateOf("")

    private val _searchedNewsResponse =
        mutableStateOf(NewsResponse())
    val searchedNewsResponse: MutableState<NewsResponse>
        @Composable get() = remember {
            _searchedNewsResponse
        }


    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)


    suspend fun getArticles(country: String): NewsResponse = withContext(Dispatchers.IO) {
        service.getTopArticles(
            country,
            "d2691289ff474bb9850b71fa026ce470"
        )
    }

    suspend fun getArticlesBySource(source: String): NewsResponse =
        withContext(Dispatchers.IO) {
            service.searchArticlesBySource(
                source = source,
                "d2691289ff474bb9850b71fa026ce470"
            )
        }


    suspend fun getSearchedArticles(query: String): NewsResponse =
        withContext(Dispatchers.IO) {
            service.searchArticles(
                query,
                "d2691289ff474bb9850b71fa026ce470"
            )
        }


    suspend fun getArticlesByCategory(category: String): NewsResponse =
        withContext(Dispatchers.IO) {
            service.getCategories(
                category,
                "d2691289ff474bb9850b71fa026ce470"
            )
        }


    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

}