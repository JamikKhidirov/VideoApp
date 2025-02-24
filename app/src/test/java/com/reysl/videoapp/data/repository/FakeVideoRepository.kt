package com.reysl.videoapp.data.repository

import com.reysl.videoapp.domain.model.VideoItem
import com.reysl.videoapp.domain.repository.VideoRepository
import com.reysl.videoapp.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeVideoRepository : VideoRepository {

    private val videos = mutableListOf<VideoItem>()

    private var shouldReturnError = false

    private var simulateDelay = false

    fun addVideos(vararg videoItems: VideoItem) {
        videos.addAll(videoItems)
    }


    fun setShouldReturnError(shouldReturnError: Boolean) {
        this.shouldReturnError = shouldReturnError
    }

    fun setSimulateDelay(simulateDelay: Boolean) {
        this.simulateDelay = simulateDelay
    }

    override suspend fun getVideoResult(): Flow<Result<List<VideoItem>>> = flow {
        if (simulateDelay) {
            kotlinx.coroutines.delay(1000)
        }

        if (shouldReturnError) {
            emit(Result.Error("Fake error occurred"))
            return@flow
        }

        emit(Result.Success(videos))
    }

}