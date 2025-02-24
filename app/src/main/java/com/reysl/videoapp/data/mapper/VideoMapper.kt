package com.reysl.videoapp.data.mapper

import com.reysl.videoapp.domain.model.VideoItem
import com.reysl.videoapp.data.remote.dto.VideoItemDto

fun VideoItemDto.toVideoItem() = VideoItem(
    id = id,
    author = author,
    description = description,
    duration = duration,
    title = title,
    views = views,
    videoUrl = videoUrl,
    thumbnailUrl = thumbnailUrl,
)