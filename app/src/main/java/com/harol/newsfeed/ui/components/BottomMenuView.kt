package com.harol.newsfeed.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.harol.newsfeed.R
import com.harol.newsfeed.data.sealed.BottomMenuScreen


@ExperimentalAnimationApi
@Composable
fun BottomMenuView(
    navController: NavController,
    showBottomNavBar: Boolean
) {
    val menuItems = listOf(
        BottomMenuScreen.TopNews,
        BottomMenuScreen.Categories,
        BottomMenuScreen.Sources,
    )
    AnimatedVisibility(
        visible = showBottomNavBar,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation(
                contentColor = colorResource(id = R.color.white)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                menuItems.forEach {
                    BottomNavigationItem(
                        label = {
                            Text(text = it.title)
                        },
                        alwaysShowLabel = true,
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.Gray,
                        selected = currentRoute == it.route,
                        onClick = {
                            navController.navigate(it.route) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = it.title
                            )
                        },
                    )
                }
            }
        })


}