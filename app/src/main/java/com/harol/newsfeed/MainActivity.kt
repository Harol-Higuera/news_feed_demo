package com.harol.newsfeed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.harol.newsfeed.ui.screens.main.MainViewModel
import com.harol.newsfeed.ui.screens.main.MainScreen
import com.harol.newsfeed.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTopNews()
        setContent {
            NewsAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val scrollState = rememberScrollState()
                    val navController = rememberNavController()
                    MainScreen(
                        navController = navController,
                        scrollState = scrollState,
                        mainViewModel = viewModel
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewsAppTheme {
        val scrollState = rememberScrollState()
        val navController = rememberNavController()
        MainScreen(
            navController = navController,
            scrollState = scrollState,
            mainViewModel = viewModel()
        )
    }
}