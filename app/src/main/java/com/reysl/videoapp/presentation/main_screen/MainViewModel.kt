package com.reysl.videoapp.presentation.main_screen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reysl.videoapp.data.cache.VideoCacheManager
import com.reysl.videoapp.domain.usecases.VideoUseCases
import com.reysl.videoapp.utils.NetworkUtils
import com.reysl.videoapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val videoUseCases: VideoUseCases,
    private val videoCacheManager: VideoCacheManager,
    private val application: Application
): ViewModel() {

    private val _state = MutableStateFlow(MainStates())
    val state = _state.asStateFlow()

    init {
        loadVideoResult()
    }

    private var isRefreshing: Boolean
        get() = _state.value.isRefreshing
        set(value) {
            _state.update {
                it.copy(isRefreshing = value)
            }
        }

    fun onEvent(event: MainEvents) {
        when (event) {
            is MainEvents.RefreshData -> refreshData()
        }
    }

    private fun refreshData() {
        isRefreshing = true
        loadVideoResult()
        isRefreshing = false
    }

    private fun loadVideoResult() {
        viewModelScope.launch {
            if (NetworkUtils.isInternetAvailable(application)) {
                videoUseCases.getVideos().collect { result ->
                    when (result) {
                        is Result.Error -> {
                            val cachedVideos = videoCacheManager.getVideos()
                            _state.update {
                                it.copy(
                                    videoItems = cachedVideos,
                                    isLoading = false
                                )
                            }
                        }

                        is Result.Loading -> {
                            _state.update {
                                it.copy(isLoading = result.isLoading)
                            }
                        }

                        is Result.Success -> {
                            result.data?.let { videos ->
                                videoCacheManager.saveVideo(videos)
                                _state.update {
                                    it.copy(
                                        videoItems = videos, isLoading = false
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                val cachedVideos = videoCacheManager.getVideos()
                _state.update {
                    it.copy(
                        videoItems = cachedVideos,
                        isLoading = false
                    )
                }
            }
        }
    }
}