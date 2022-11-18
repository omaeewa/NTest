package com.miracle.natifetest.presentation.gifinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.miracle.natifetest.R
import com.miracle.natifetest.presentation.home.HomeViewModel
import com.miracle.natifetest.presentation.theme.DarkBlue
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalPagerApi::class)
@Composable
fun GifInfoScreen(
    vm: HomeViewModel = hiltViewModel(),
    gifIndex: Int, navigateBack: () -> Unit
) {
    val uiState by vm.uiState.collectAsState()
    val gifs = uiState.gifUrls


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
                    builder.placeholder(context.resources.getDrawable(R.drawable.ic_gif))
                }

                Button(
                    onClick = {
                        navigateBack()
                        vm.blockGif(gifs[it])
                    }, modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.BottomEnd),
                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlue),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "Don't show it again", color = Color.White)
                }
            }
        }


    }
}