package com.harol.newsfeed.ui.screens.main

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.harol.newsfeed.data.sealed.BottomMenuScreen
import com.harol.newsfeed.models.Articles
import com.harol.newsfeed.ui.MainViewModel
import com.harol.newsfeed.ui.components.BottomMenuView
import com.harol.newsfeed.ui.screens.categories.CategoriesScreen
import com.harol.newsfeed.ui.screens.categories.CategoriesViewModel
import com.harol.newsfeed.ui.screens.newsDetails.NewsDetailsScreen
import com.harol.newsfeed.ui.screens.sources.SourcesScreen
import com.harol.newsfeed.ui.screens.sources.SourcesViewModel
import com.harol.newsfeed.ui.screens.topNews.TopNewsScreen

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
            mainViewModel = mainViewModel
        )
    }
}


@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel
) {
    val loading by mainViewModel.isLoading.collectAsState()
    val error by mainViewModel.isError.collectAsState()
    val articles = mutableListOf<Articles>()
    val topArticles = mainViewModel.newsResponse.collectAsState().value.articles
    articles.addAll(topArticles ?: listOf())

    NavHost(
        navController = navController,
        startDestination = BottomMenuScreen.TopNews.route,
        modifier = Modifier.padding(paddingValues)
    ) {

        val queryState = mutableStateOf(mainViewModel.query.value)
        val isLoading = mutableStateOf(loading)
        val isError = mutableStateOf(error)

        /**
         * Composable: Bottom Menu - Top News
         **/
        composable(BottomMenuScreen.TopNews.route) {
            TopNewsScreen(
                navController = navController,
                articles = articles,
                query = queryState,
                viewModel = mainViewModel,
                isLoading = isLoading,
                isError = isError
            )
        }

        /**
         * Composable: Bottom Menu - Categories
         **/
        composable(BottomMenuScreen.Categories.route) {
            val categoriesModel = viewModel<CategoriesViewModel>()
            categoriesModel.getArticlesByCategory("business")
            categoriesModel.onSelectedCategory("business")
            CategoriesScreen(
                viewModel = categoriesModel,
                onFetchCategory = {
                    categoriesModel.onSelectedCategory(it)
                    categoriesModel.getArticlesByCategory(it)
                },
            )
        }

        /**
         * Composable: Bottom Menu - Sources
         **/
        composable(BottomMenuScreen.Sources.route) {
            val sourcesViewModel = viewModel<SourcesViewModel>()
            sourcesViewModel.getArticlesBySource()
            SourcesScreen(
                viewModel = sourcesViewModel,
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
                    articles.addAll(mainViewModel.searchNewsResponse.value.articles ?: listOf())
                } else {
                    articles.clear()
                    articles.addAll(mainViewModel.newsResponse.value.articles ?: listOf())
                }
                val article = articles[index]
                NewsDetailsScreen(article, scrollState, navController)
            }
        }
    }
}