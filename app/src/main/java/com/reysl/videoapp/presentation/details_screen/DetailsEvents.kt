package com.reysl.videoapp.presentation.details_screen

sealed class DetailsEvents {
    data class PlayVideo(val url: String) : DetailsEvents()
    data object SavePlayerState : DetailsEvents()
    data object PauseVideo : DetailsEvents()
}