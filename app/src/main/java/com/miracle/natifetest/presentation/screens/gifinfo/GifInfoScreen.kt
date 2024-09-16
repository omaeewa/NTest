package com.miracle.natifetest.presentation.screens.gifinfo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.miracle.natifetest.R
import com.miracle.natifetest.presentation.DarkBlue
import com.miracle.natifetest.presentation.screens.home.GifImage

@Composable
fun GifInfoScreen(
    gifIndex: Int,
    navigateBack: () -> Unit
) {
    val vm: GifInfoViewModel = hiltViewModel()
    val gifs by vm.gifUrls.collectAsState(initial = emptyList())
    val pagerState = rememberPagerState(initialPage = gifIndex) { gifs.size }

    Column {
        IconButton(onClick = navigateBack, modifier = Modifier.padding(start = 10.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = null
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                GifImage(
                    model = gifs[it],
                    modifier = Modifier.align(Alignment.Center)
                )

                Button(
                    onClick = {
                        vm.moveGitToHidden(gifs[it])
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