package com.miracle.natifetest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.miracle.natifetest.presentation.screens.gifinfo.gifInfoScreen
import com.miracle.natifetest.presentation.screens.gifinfo.navigateToGifInfo
import com.miracle.natifetest.presentation.screens.home.HomeRoute
import com.miracle.natifetest.presentation.screens.home.homeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NatifeTestTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NatifeTestAppNavHost()
                }
            }
        }
    }
}

@Composable
fun NatifeTestAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Any = HomeRoute
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        homeScreen(navigateToGifInfo = navController::navigateToGifInfo)
        gifInfoScreen(navigateBack = navController::popBackStack)
    }
}


