package com.reysl.videoapp.di

import android.app.Application
import com.reysl.videoapp.constants.PlayerConstants
import com.reysl.videoapp.data.api.VideoApi
import com.reysl.videoapp.data.repository.VideoRepositoryImpl
import com.reysl.videoapp.domain.repository.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VideoAppModule {

    @Provides
    @Singleton
    fun providesVideoApi(): VideoApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PlayerConstants.BASE_URL)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesVideoRepository(
        videoApi: VideoApi,
        application: Application
    ): VideoRepository = VideoRepositoryImpl(videoApi = videoApi, application = application)
}