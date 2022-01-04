package com.harol.newsfeed.screens.main

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.harol.newsfeed.components.BottomMenuView
import com.harol.newsfeed.data.sealed.BottomMenuScreen
import com.harol.newsfeed.models.Articles
import com.harol.newsfeed.screens.topNews.TopNews
import com.harol.newsfeed.ui.MainViewModel
import com.harol.newsfeed.ui.screen.Categories
import com.harol.newsfeed.ui.screen.DetailScreen
import com.harol.newsfeed.ui.screen.Sources


@Composable
fun MainScreen(
    navController: NavHostController,
    scrollState: ScrollState,
    mainViewModel: MainViewModel
) {
    Scaffold(bottomBar = {
        BottomMenuView(navController = navController)
    }) {
        Navigation(
            navController = navController,
            scrollState = scrollState,
            paddingValues = it,
            viewModel = mainViewModel
        )
    }
}


@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    viewModel: MainViewModel
) {
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.isError.collectAsState()
    val articles = mutableListOf<Articles>()
    val topArticles = viewModel.newsResponse.collectAsState().value.articles
    articles.addAll(topArticles ?: listOf())

    NavHost(
        navController = navController,
        startDestination = BottomMenuScreen.TopNews.route,
        modifier = Modifier.padding(paddingValues)
    ) {

        val queryState = mutableStateOf(viewModel.query.value)
        val isLoading = mutableStateOf(loading)
        val isError = mutableStateOf(error)

        /**
         * Composable: Bottom Menu - Top News
         **/
        composable(BottomMenuScreen.TopNews.route) {
            TopNews(
                navController = navController,
                articles = articles,
                query = queryState,
                viewModel = viewModel,
                isLoading = isLoading,
                isError = isError
            )
        }

        /**
         * Composable: Bottom Menu - Categories
         **/
        composable(BottomMenuScreen.Categories.route) {
            viewModel.getArticlesByCategory("business")
            viewModel.onSelectedCategory("business")
            Categories(
                viewModel = viewModel,
                onFetchCategory = {
                    viewModel.onSelectedCategory(it)
                    viewModel.getArticlesByCategory(it)
                },
                isLoading = isLoading,
                isError = isError
            )
        }

        /**
         * Composable: Bottom Menu - Sources
         **/
        composable(BottomMenuScreen.Sources.route) {
            Sources(
                viewModel = viewModel,
                isLoading = isLoading,
                isError = isError
            )
        }

        /**
         * Composable: Bottom Menu - News Details
         **/
        composable("Detail/{index}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType }
            )) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            index?.let {
                if (queryState.value != "") {
                    articles.clear()
                    articles.addAll(viewModel.searchNewsResponse.value.articles ?: listOf())
                } else {
                    articles.clear()
                    articles.addAll(viewModel.newsResponse.value.articles ?: listOf())
                }
                val article = articles[index]
                DetailScreen(article, scrollState, navController)
            }
        }
    }
}