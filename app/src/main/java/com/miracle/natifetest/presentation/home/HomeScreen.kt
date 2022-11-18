package com.miracle.natifetest.presentation.home

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.miracle.natifetest.presentation.common.composable.SearchEditText
import com.miracle.natifetest.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel(),
    navigateToGifInfo: (gifIndex: Int) -> Unit
) {
    val uiState by vm.uiState.collectAsState()
    val context = LocalContext.current

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
                    word = uiState.searchString,
                    onValueChange = vm::onSearchStringUpdate
                )
            }
            IconButton(
                onClick = vm::updateListViewType,
                modifier = Modifier.padding(end = 10.dp)
            ) {
                val icon = when (uiState.listViewType) {
                    ListViewType.Table -> R.drawable.ic_table_rows
                    ListViewType.Column -> R.drawable.ic_table_chart
                }
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null
                )
            }
        }

        when (uiState.listViewType) {
            ListViewType.Table -> {
                val state = rememberLazyGridState()
                LazyVerticalGrid(
                    state = state,
                    columns = GridCells.Fixed(3),
                ) {
                    items(uiState.gifUrls.size) {
                        if (it == uiState.gifUrls.size - 1) vm.fetchGifs()

                        GlideImage(
                            model = uiState.gifUrls[it],
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .padding(5.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    navigateToGifInfo(it)
                                },
                            contentScale = ContentScale.Crop
                        ) { builder ->
                            builder.placeholder(context.resources.getDrawable(R.drawable.ic_gif))
                        }
                    }

                }
            }
            ListViewType.Column -> {
                val state = rememberLazyListState()
                LazyColumn(state = state) {
                    items(uiState.gifUrls.size) {
                        if (it == uiState.gifUrls.size - 1) vm.fetchGifs()

                        GlideImage(
                            model = uiState.gifUrls[it],
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { navigateToGifInfo(it) },
                            contentScale = ContentScale.FillWidth
                        ) { builder ->
                            builder.placeholder(context.resources.getDrawable(R.drawable.ic_gif))
                        }
                    }
                }
            }
        }

        if (uiState.gifUrls.isEmpty() && !uiState.loading) Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.ic_notes),
                    contentDescription = null,
                    Modifier.size(250.dp)
                )
                Text(text = "Write the name of the gif in the search bar", color = Color.White)
            }

        }

        if (uiState.loading) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
}

