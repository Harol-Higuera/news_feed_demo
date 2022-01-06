package com.harol.newsfeed.ui.screens.newsDetails

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.harol.newsfeed.R
import com.harol.newsfeed.models.Articles
import com.harol.newsfeed.utils.DateUtils
import com.harol.newsfeed.utils.DateUtils.getTimeAgo
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun NewsDetailsScreen(
    articles: Articles,
    scrollState: ScrollState,
    navController: NavController
) {
    Scaffold(topBar = {
        DetailTopAppBar(onBackPressed = {
            navController.popBackStack()
        })
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CoilImage(
                imageModel = articles.urlToImage,
                // Crop, Fit, Inside, FillHeight, FillWidth, None
                contentScale = ContentScale.Crop,
                // shows a placeholder ImageBitmap when loading.
                placeHolder = ImageBitmap.imageResource(R.drawable.breaking_news)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoWithIcon(Icons.Default.Edit, info = articles.author ?: "Not Available")
                articles.publishedAt?.let { publishedAt ->
                    DateUtils.stringToDate(publishedAt)?.let { it ->
                        InfoWithIcon(
                            icon = Icons.Default.DateRange,
                            info = it.getTimeAgo()
                        )
                    }
                }
            }
            Text(text = articles.title ?: "Not Available", fontWeight = FontWeight.Bold)
            Text(
                text = articles.description ?: "Not Available",
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun DetailTopAppBar(
    onBackPressed: () -> Unit = {}
) {
    TopAppBar(title = { Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold) },
        navigationIcon = {
            IconButton(onClick = {
                onBackPressed()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back")
            }
        })
}

@Composable
fun InfoWithIcon(
    icon: ImageVector,
    info: String
) {
    Row {
        Icon(
            icon,
            contentDescription = "Author",
            modifier = Modifier.padding(end = 8.dp),
            colorResource(
                id = R.color.purple_500
            )
        )
        Text(text = info)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    NewsDetailsScreen(
        Articles(
            author = "Namita Singh",
            title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
            description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
            publishedAt = "2021-11-04T04:42:40Z"
        ), rememberScrollState(),
        rememberNavController()
    )
}