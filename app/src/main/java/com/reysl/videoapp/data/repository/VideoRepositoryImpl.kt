package com.reysl.videoapp.data.repository

import android.app.Application
import com.reysl.videoapp.R
import com.reysl.videoapp.data.api.VideoApi
import com.reysl.videoapp.data.mapper.toVideoItem
import com.reysl.videoapp.domain.model.VideoItem
import com.reysl.videoapp.domain.repository.VideoRepository
import com.reysl.videoapp.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoApi: VideoApi,
    private val application: Application
): VideoRepository {
    override suspend fun getVideoResult(): Flow<Result<List<VideoItem>>> = flow {
        emit(Result.Loading(true))

        try {
            val remoteVideoResultDto = videoApi.getVideoResult()

            val videoItems = remoteVideoResultDto.map { it.toVideoItem() }

            emit(Result.Success(videoItems))
        } catch (e: HttpException) {
            emit(Result.Error(application.getString(R.string.network_error)))
        } catch (e: IOException) {
            emit(Result.Error(application.getString(R.string.error_input_output)))
        } catch (e: Exception) {
            emit(Result.Error(application.getString(R.string.unknown_error)))
        } finally {
            emit(Result.Loading(false))
        }
    }
}