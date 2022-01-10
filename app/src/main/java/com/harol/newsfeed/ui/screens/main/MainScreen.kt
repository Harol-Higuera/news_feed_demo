package com.harol.newsfeed.ui.screens.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.harol.newsfeed.data.sealed.BottomMenuScreen
import com.harol.newsfeed.ui.components.BottomMenuView
import com.harol.newsfeed.ui.screens.categories.CategoriesScreen
import com.harol.newsfeed.ui.screens.categories.CategoriesViewModel
import com.harol.newsfeed.ui.screens.newsDetails.NewsDetailsScreen
import com.harol.newsfeed.ui.screens.sources.SourcesScreen
import com.harol.newsfeed.ui.screens.sources.SourcesViewModel
import com.harol.newsfeed.ui.screens.topNews.TopNewsScreen
import com.harol.newsfeed.ui.screens.topNews.TopNewsViewModel

@ExperimentalAnimationApi
@Composable
fun MainScreen() {

    val topNewsViewModel = viewModel<TopNewsViewModel>()

    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    var showBottomNavBar by remember {
        mutableStateOf(true)
    }

    Scaffold(
        bottomBar = {
            BottomMenuView(
                navController = navController,
                showBottomNavBar = showBottomNavBar
            )
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = BottomMenuScreen.TopNews.route,
            modifier = Modifier.padding(it)
        ) {
            /**
             * Composable: Bottom Menu - Top News
             **/
            composable(BottomMenuScreen.TopNews.route) {
                showBottomNavBar = true
                topNewsViewModel.getTopNews()
                TopNewsScreen(
                    navController = navController,
                    viewModel = topNewsViewModel,
                )
            }

            /**
             * Composable: Bottom Menu - Categories
             **/
            composable(BottomMenuScreen.Categories.route) {
                showBottomNavBar = true
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
                showBottomNavBar = true
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
                            showBottomNavBar = false
                            NewsDetailsScreen(articles[nonNullIndex], scrollState, navController)
                        }
                    }
                }
            }
        }
    }
}