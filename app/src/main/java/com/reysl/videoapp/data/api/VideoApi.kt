package com.reysl.videoapp.data.api

import com.reysl.videoapp.data.remote.dto.VideoResultDto
import retrofit2.http.GET

interface VideoApi {
    @GET("videos.json")
    suspend fun getVideoResult(): VideoResultDto
}