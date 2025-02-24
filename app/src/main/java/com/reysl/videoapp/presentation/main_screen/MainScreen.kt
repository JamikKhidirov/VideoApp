@file:Suppress("DEPRECATION")

package com.reysl.videoapp.presentation.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.reysl.videoapp.domain.model.VideoItem
import com.reysl.videoapp.presentation.common.EmptyScreen
import com.reysl.videoapp.presentation.common.VideoCard
import com.reysl.videoapp.presentation.common.VideoCardShimmerEffect
import com.reysl.videoapp.ui.theme.VideoAppTheme

@Composable
fun MainScreen(mainViewModel: MainViewModel, navigateToDetails: (VideoItem) -> Unit) {

    val state = mainViewModel.state.collectAsState()

    val error = state.value.error

    VideoAppTheme {
        SwipeRefresh(
            state = rememberSwipeRefreshState(state.value.isRefreshing),
            onRefresh = { mainViewModel.onEvent(MainEvents.RefreshData) },
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                if (state.value.isLoading) {
                    ShimmerEffect()
                } else if (error != null) {
                    EmptyScreen(mainViewModel, error)
                } else {
                    val videoItems = state.value.videoItems

                    LazyColumn(contentPadding = PaddingValues(16.dp)) {
                        items(
                            items = videoItems,
                            key = { videoItem -> videoItem.id }
                        ) { item ->
                            VideoCard(video = item, onClick = {
                                navigateToDetails(item)
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ShimmerEffect() {
    Column {
        repeat(8) {
            VideoCardShimmerEffect()
        }
    }
}