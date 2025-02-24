package com.reysl.videoapp.data.remote.dto

data class VideoItemDto(
    val author: String = "",
    val description: String = "",
    val duration: String = "",
    val id: String = "",
    val isLive: Boolean = false,
    val subscriber: String = "",
    val thumbnailUrl: String = "",
    val title: String = "",
    val uploadTime: String = "",
    val videoUrl: String = "",
    val views: String = ""
)