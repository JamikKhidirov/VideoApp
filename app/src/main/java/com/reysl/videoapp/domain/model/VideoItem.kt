package com.reysl.videoapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoItem(
    val id: String,
    val author: String,
    val description: String,
    val duration: String,
    val title: String,
    val views: String,
    val videoUrl: String,
    val thumbnailUrl: String
): Parcelable
