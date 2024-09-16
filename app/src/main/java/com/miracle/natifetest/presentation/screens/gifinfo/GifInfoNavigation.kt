package com.miracle.natifetest.presentation.screens.gifinfo

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable


@Serializable
data class GifInfoRoute(val gifId: Int)

fun NavController.navigateToGifInfo(gifIndex: Int) {
    navigate(GifInfoRoute(gifIndex)) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.gifInfoScreen(navigateBack: () -> Unit) {
    composable<GifInfoRoute> {
        val gifIndex = it.toRoute<GifInfoRoute>().gifId

        GifInfoScreen(gifIndex = gifIndex, navigateBack = navigateBack)
    }
}