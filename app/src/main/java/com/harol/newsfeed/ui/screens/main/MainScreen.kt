package com.harol.newsfeed.ui.screens.main

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.harol.newsfeed.data.sealed.BottomMenuScreen
import com.harol.newsfeed.ui.components.BottomMenuView
import com.harol.newsfeed.ui.screens.categories.CategoriesScreen
import com.harol.newsfeed.ui.screens.categories.CategoriesViewModel
import com.harol.newsfeed.ui.screens.newsDetails.NewsDetailsScreen
import com.harol.newsfeed.ui.screens.sources.SourcesScreen
import com.harol.newsfeed.ui.screens.sources.SourcesViewModel
import com.harol.newsfeed.ui.screens.topNews.TopNewsViewModel
import com.harol.newsfeed.ui.screens.topNews.TopNewsScreen

@Composable
fun MainScreen(
    navController: NavHostController,
    scrollState: ScrollState,
    topNewsViewModel: TopNewsViewModel
) {
    Scaffold(bottomBar = {
        BottomMenuView(navController = navController)
    }) {
        Navigation(
            navController = navController,
            scrollState = scrollState,
            paddingValues = it,
            topNewsViewModel = topNewsViewModel
        )
    }
}


@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    topNewsViewModel: TopNewsViewModel
) {


    NavHost(
        navController = navController,
        startDestination = BottomMenuScreen.TopNews.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        /**
         * Composable: Bottom Menu - Top News
         **/
        composable(BottomMenuScreen.TopNews.route) {
            TopNewsScreen(
                navController = navController,
                viewModel = topNewsViewModel,
            )
        }

        /**
         * Composable: Bottom Menu - Categories
         **/
        composable(BottomMenuScreen.Categories.route) {
            val categoriesModel = viewModel<CategoriesViewModel>()
            categoriesModel.getNewsByCategory()
            CategoriesScreen(
                viewModel = categoriesModel,
            )
        }

        /**
         * Composable: Bottom Menu - Sources
         **/
        composable(BottomMenuScreen.Sources.route) {
            val sourcesViewModel = viewModel<SourcesViewModel>()
            sourcesViewModel.getNewsBySource()
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
            index?.let { nonNullIndex ->
                val currentNews = topNewsViewModel.newsResponse.collectAsState().value
                currentNews.articles?.let { articles ->
                    if (articles.isNotEmpty()) {
                        NewsDetailsScreen(articles[nonNullIndex], scrollState, navController)
                    }
                }
            }
        }
    }
}