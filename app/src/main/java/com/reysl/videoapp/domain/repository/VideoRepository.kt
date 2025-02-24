package com.reysl.videoapp.domain.repository

import com.reysl.videoapp.domain.model.VideoItem
import com.reysl.videoapp.utils.Result
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    suspend fun getVideoResult(): Flow<Result<List<VideoItem>>>
}