package com.harol.newsfeed.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.harol.newsfeed.R
import com.harol.newsfeed.components.ErrorUI
import com.harol.newsfeed.components.LoadingUI
import com.harol.newsfeed.components.SearchBar
import com.harol.newsfeed.models.Articles
import com.harol.newsfeed.ui.MainViewModel
import com.harol.newsfeed.utils.DateUtils
import com.harol.newsfeed.utils.DateUtils.getTimeAgo
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun TopNews(
    navController: NavController,
    articles: List<Articles>,
    query: MutableState<String>,
    viewModel: MainViewModel,
    isLoading: MutableState<Boolean>,
    isError: MutableState<Boolean>
) {
    val searchedText = query.value
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        SearchBar(query, viewModel)
        val resultList = mutableListOf<Articles>()
        if (searchedText != "") {
            resultList.addAll(
                viewModel.searchNewsResponse.collectAsState().value.articles ?: listOf(Articles())
            )
        } else {
            resultList.addAll(articles)
        }

        when {
            isLoading.value -> {
                LoadingUI()
            }
            isError.value -> {
                ErrorUI()
            }
            else -> {
                LazyColumn {
                    items(resultList.size) { index ->
                        TopNewsItem(articles = resultList[index],
                            onNewsClick = { navController.navigate("Detail/${index}") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopNewsItem(articles: Articles, onNewsClick: () -> Unit = {}) {
    Box(modifier = Modifier
        .height(200.dp)
        .padding(8.dp)
        .clickable {
            onNewsClick()
        }) {
        CoilImage(
            imageModel = articles.urlToImage,
            // Crop, Fit, Inside, FillHeight, FillWidth, None
            contentScale = ContentScale.Crop,
            // shows a placeholder ImageBitmap when loading.
            placeHolder = ImageBitmap.imageResource(R.drawable.breaking_news)
        )
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 16.dp, start = 16.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {
            articles.publishedAt?.let { publishedAt ->
                DateUtils.stringToDate(publishedAt)?.let {
                    Text(
                        text = it.getTimeAgo(),
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = articles.title ?: "Not Available",
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TopNewsPreview() {
    TopNewsItem(
        Articles(
            author = "Namita Singh",
            title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
            description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
            publishedAt = "2021-11-04T04:42:40Z"
        )
    )
}