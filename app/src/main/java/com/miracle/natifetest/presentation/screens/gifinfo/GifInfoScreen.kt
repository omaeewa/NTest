package com.miracle.natifetest.presentation.screens.gifinfo

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.miracle.natifetest.R
import com.miracle.natifetest.presentation.screens.home.HomeViewModel
import com.miracle.natifetest.presentation.DarkBlue
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun GifInfoScreen(
    gifIndex: Int,
    navigateBack: () -> Unit
) {
    val vm: HomeViewModel = hiltViewModel()
    val uiState by vm.uiState.collectAsState()
    val gifs = uiState.gifUrls

    LaunchedEffect(uiState) {
        Log.d("kek", "GifInfoScreen: ${uiState.gifUrls.size}")
        Log.d("kek", "GifInfoScreen: ${vm.hashCode()}")
    }


    val context = LocalContext.current
    Column {
        IconButton(onClick = navigateBack, modifier = Modifier.padding(start = 10.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = null
            )
        }
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                pagerState.scrollToPage(gifIndex)
            }
        }

        HorizontalPager(
            count = gifs.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {

                GlideImage(
                    model = gifs[it],
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .align(Alignment.Center),
                    contentScale = ContentScale.FillWidth
                ) { builder ->
                    builder.placeholder(ContextCompat.getDrawable(context, R.drawable.ic_gif))
                }

                Button(
                    onClick = {
                        vm.blockGif(gifs[it])
                    }, modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.BottomEnd),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkBlue),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = stringResource(id = R.string.dont_show_it), color = Color.White)
                }
            }
        }


    }
}