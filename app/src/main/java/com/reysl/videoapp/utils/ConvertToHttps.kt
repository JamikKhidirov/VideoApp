package com.reysl.videoapp.utils

fun convertToHttps(url: String): String {
    return if (url.startsWith("http://")) {
        url.replace("http://", "https://")
    } else {
        url
    }
}