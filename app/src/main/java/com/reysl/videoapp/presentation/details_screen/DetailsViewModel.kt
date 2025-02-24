package com.reysl.videoapp.presentation.details_screen

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.reysl.videoapp.constants.PlayerConstants
import com.reysl.videoapp.data.cache.VideoCacheManager
import com.reysl.videoapp.utils.NetworkUtils
import com.reysl.videoapp.utils.convertToHttps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val player: Player,
    private val savedStateHandle: SavedStateHandle,
    private val videoCacheManager: VideoCacheManager,
    private val application: Application
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsStates())
    val state = _state.asStateFlow()

    private var currentPosition: Long
        get() = savedStateHandle[PlayerConstants.KEY_CURRENT_POSITION]
            ?: PlayerConstants.DEFAULT_VIDEO_POSITION
        set(value) {
            savedStateHandle[PlayerConstants.KEY_CURRENT_POSITION] = value
        }

    private var isPlaying: Boolean
        get() = savedStateHandle[PlayerConstants.KEY_IS_PLAYING]
            ?: PlayerConstants.DEFAULT_IS_PLAYING
        set(value) {
            savedStateHandle[PlayerConstants.KEY_IS_PLAYING] = value
        }

    init {
        player.prepare()
        player.playWhenReady = true
    }

    fun onEvent(event: DetailsEvents) {
        when (event) {
            is DetailsEvents.PlayVideo -> playVideo(event.url)
            is DetailsEvents.SavePlayerState -> savePlayerState()
            is DetailsEvents.PauseVideo -> pauseVideo()
        }

    }

    private fun playVideo(url: String) {
        if (videoCacheManager.isVideoCached(url)) {
            val cachedFile = videoCacheManager.getCachedFile(url)
            cachedFile?.let {
                playMedia(it.absolutePath)
                return
            }
        }
        if (NetworkUtils.isInternetAvailable(application)) {
            playMedia(url)
        } else {
            val cachedVideos = videoCacheManager.getVideos()
            val cachedVideo = cachedVideos.find { convertToHttps(it.videoUrl) == url }
            cachedVideo?.let {
                playMedia(convertToHttps(it.videoUrl))
            }
        }
    }

    private fun playMedia(path: String) {
        val url = convertToHttps(path)
        val mediaItem = MediaItem.fromUri(url)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.seekTo(currentPosition)
        if (isPlaying) {
            player.play()
        }
        _state.update { it.copy(url = path, isPlaying = true, error = null) }
    }

    private fun pauseVideo() {
        player.pause()
        _state.update { it.copy(isPlaying = false) }
    }

    private fun savePlayerState() {
        currentPosition = player.currentPosition
        isPlaying = player.isPlaying
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}