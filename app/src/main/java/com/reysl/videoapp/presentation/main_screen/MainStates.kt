package com.reysl.videoapp.presentation.main_screen

import com.reysl.videoapp.domain.model.VideoItem

data class MainStates(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val videoItems: List<VideoItem> = emptyList()
)
