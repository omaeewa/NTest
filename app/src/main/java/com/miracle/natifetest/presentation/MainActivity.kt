package com.miracle.natifetest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.miracle.natifetest.presentation.gifinfo.GifInfoScreen
import com.miracle.natifetest.presentation.home.HomeScreen
import com.miracle.natifetest.presentation.home.HomeViewModel
import com.miracle.natifetest.presentation.theme.NatifeTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NatifeTestTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
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
    startDestination: String = Screen.Home.name
) {
    val vm: HomeViewModel = hiltViewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Screen.Home.name) {
            val navigateToGifIngo = { gifIndex: Int ->
                navController.navigate(Screen.GifInfo.name.addArgs(ARGUMENT_GIF_INDEX, gifIndex.toString()))
            }
            HomeScreen(vm = vm, navigateToGifInfo = navigateToGifIngo)
        }

        composable(
            Screen.GifInfo.name.addPathArgs(ARGUMENT_GIF_INDEX),
            arguments = listOf(navArgument(ARGUMENT_GIF_INDEX) { type = NavType.IntType })
        ) {
            val gifIndex = it.arguments?.getInt(ARGUMENT_GIF_INDEX) ?: 0
            GifInfoScreen(vm = vm, gifIndex = gifIndex) {
                navController.popBackStack()
            }
        }
    }

}


