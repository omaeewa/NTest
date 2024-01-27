package com.miracle.natifetest.presentation.screens.gifinfo

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val GIF_INDEX = "gif_index"
const val gifInfoNavigationRoute = "gif_info_route"

fun NavController.navigateToGifInfo(gifIndex: Int) {
    this.navigate("$gifInfoNavigationRoute/$gifIndex") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.gifInfoScreen(navigateBack: () -> Unit) {
    composable(
        route = "$gifInfoNavigationRoute/{$GIF_INDEX}",
        arguments = listOf(
            navArgument(GIF_INDEX) { type = NavType.IntType}
        )
    ) {
        val gifIndex = it.arguments?.getInt(GIF_INDEX) ?: 0

        GifInfoScreen(gifIndex = gifIndex, navigateBack = navigateBack)
    }
}