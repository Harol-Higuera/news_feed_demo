package com.harol.newsfeed.ui.screens.categories

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.harol.newsfeed.R
import com.harol.newsfeed.data.enums.ArticleCategory
import com.harol.newsfeed.data.enums.allArticleCategories
import com.harol.newsfeed.models.api.Articles
import com.harol.newsfeed.ui.components.ErrorView
import com.harol.newsfeed.ui.components.LoadingView
import com.harol.newsfeed.utils.DateUtils
import com.harol.newsfeed.utils.DateUtils.getTimeAgo


@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel,
) {
    val tabsItems = allArticleCategories()

    val isLoading = viewModel.isLoading.collectAsState()
    val isError = viewModel.isError.collectAsState()
    val articles = viewModel.newsResponse.collectAsState().value.articles ?: listOf()

    Column {

        if (articles.isNotEmpty()) {
            LazyRow {
                items(tabsItems.size) {
                    val category = tabsItems[it]
                    CategoryTab(
                        category = category,
                        onFetchCategory = { selectedCategory ->
                            viewModel.getNewsByCategory(selectedCategory)
                        },
                        isSelected = viewModel.selectedCategory.collectAsState().value == category,
                    )
                }
            }
        }
        Content(
            articles = articles,
            isLoading = isLoading.value,
            isError = isError.value
        )
    }
}

@Composable
fun CategoryTab(
    category: ArticleCategory,
    isSelected: Boolean = false,
    onFetchCategory: (ArticleCategory) -> Unit
) {
    val background =
        if (isSelected) colorResource(id = R.color.purple_200) else colorResource(id = R.color.purple_700)
    Surface(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 16.dp)
            .clickable {
                onFetchCategory(category)
            },
        shape = MaterialTheme.shapes.small,
        color = background
    ) {
        Text(
            text = category.categoryName,
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier.padding(all = 8.dp)
        )
    }
}

@Composable
fun Content(
    articles: List<Articles>,
    isLoading: Boolean,
    isError: Boolean,
) {
    Box {
        ArticleItems(
            articles = articles,
            modifier = Modifier.padding(8.dp)
        )
        when {
            isLoading -> {
                LoadingView()
            }
            isError -> {
                ErrorView()
            }
        }
    }
}

@Composable
fun ArticleItems(
    articles: List<Articles>,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        if (articles.isNotEmpty()) {
            items(articles) { article ->
                Card(
                    modifier,
                    border = BorderStroke(2.dp, color = colorResource(id = R.color.purple_500))
                ) {
                    Row(modifier.fillMaxWidth()) {
                        com.skydoves.landscapist.coil.CoilImage(
                            imageModel = article.urlToImage,
                            modifier = Modifier.size(100.dp)
                        )
                        Column(modifier) {
                            Text(
                                text = article.title ?: "Not Available",
                                fontWeight = FontWeight.Bold
                            )
                            Row {
                                Text(text = article.author ?: "Not Available")
                                article.publishedAt?.let { publishedAt ->
                                    DateUtils.stringToDate(publishedAt)?.let {
                                        Text(
                                            text = it.getTimeAgo()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}