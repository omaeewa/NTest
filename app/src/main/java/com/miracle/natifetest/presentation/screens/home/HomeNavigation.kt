package com.miracle.natifetest.presentation.screens.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) = navigate(HomeRoute, navOptions)

fun NavGraphBuilder.homeScreen(navigateToGifInfo: (Int) -> Unit) {
    composable<HomeRoute> {
        HomeScreen(navigateToGifInfo = navigateToGifInfo)
    }
}