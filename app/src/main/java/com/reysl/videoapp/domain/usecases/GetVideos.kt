package com.reysl.videoapp.domain.usecases

import com.reysl.videoapp.domain.model.VideoItem
import com.reysl.videoapp.domain.repository.VideoRepository
import com.reysl.videoapp.utils.Result
import kotlinx.coroutines.flow.Flow

class GetVideos(
    private val videoRepository: VideoRepository
) {
    suspend operator fun invoke(): Flow<Result<List<VideoItem>>> {
        return videoRepository.getVideoResult()
    }
}
