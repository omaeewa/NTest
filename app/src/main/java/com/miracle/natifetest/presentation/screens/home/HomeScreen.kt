package com.miracle.natifetest.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.miracle.natifetest.R
import com.miracle.natifetest.presentation.common.composable.SearchEditText

@Composable
fun HomeScreen(
    navigateToGifInfo: (gifIndex: Int) -> Unit
) {
    val vm: HomeViewModel = hiltViewModel()
    val listViewType by vm.listViewType.collectAsState()
    val searchString by vm.searchString.collectAsState()
    val gifs by vm.gifUrls.collectAsState(emptyList())
    val isLoading by vm.isLoading.collectAsState(false)

    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(Modifier.weight(1f)) {
                SearchEditText(
                    word = searchString,
                    isLoading = isLoading,
                    onValueChange = vm::onSearchStringUpdate
                )
            }
            if (gifs.isNotEmpty())
                IconButton(
                    onClick = vm::updateListViewType,
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    val icon = when (listViewType) {
                        ListViewType.Table -> R.drawable.ic_table_rows
                        ListViewType.Column -> R.drawable.ic_table_chart
                    }
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null
                    )
                }
        }

        when (listViewType) {
            ListViewType.Table -> {
                val state = rememberLazyGridState()
                LazyVerticalGrid(
                    state = state,
                    columns = GridCells.Fixed(3),
                ) {
                    items(gifs.size) {
                        LaunchedEffect(Unit) {
                            if (it == gifs.size - 10) vm.loadMoreGifs()
                        }

                        GifImage(model = gifs[it], onClick = { navigateToGifInfo(it) })
                    }


                }
            }

            ListViewType.Column -> {
                val state = rememberLazyListState()
                LazyColumn(state = state) {
                    items(gifs.size) {
                        LaunchedEffect(Unit) {
                            if (it == gifs.size - 10) {
                                vm.loadMoreGifs()
                            }
                        }

                        GifImage(model = gifs[it], onClick = { navigateToGifInfo(it) })
                    }
                }
            }
        }

        if (gifs.isEmpty() && !isLoading)
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = null,
                        Modifier.size(180.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                    Text(
                        text = stringResource(id = R.string.search_hint),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

            }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GifImage(modifier: Modifier = Modifier, model: Any, onClick: () -> Unit = {}) {
    val context = LocalContext.current
    GlideImage(
        model = model,
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick),
        contentScale = ContentScale.Crop
    ) { builder ->
        builder.placeholder(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_gif
            )
        )
    }
}
