package com.reysl.videoapp.domain.usecases

import com.reysl.videoapp.data.repository.FakeVideoRepository
import com.reysl.videoapp.domain.model.VideoItem
import com.reysl.videoapp.utils.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetVideosTest {

    private lateinit var getVideos: GetVideos
    private lateinit var fakeRepository: FakeVideoRepository

    @Before
    fun setUp() {
        fakeRepository = FakeVideoRepository()
        getVideos = GetVideos(fakeRepository)
    }

    @Test
    fun `getVideoResult should return emptyList`() = runTest {
        val result = fakeRepository.getVideoResult().first()

        assertEquals(0, (result as Result.Success).data?.size)

    }

    @Test
    fun `getVideoResult should return videos`() = runTest {
        val video1 = VideoItem("1", "Reysl_Bruno", "Video 1 description", "15:00", "Video1", "14100", "https://example.com/video1.mp4", "https://example.com/video1.png")
        val video2 = VideoItem("2", "Reysl", "Video 2 description", "10:00", "Video2", "5000", "https://example.com/video2.mp4", "https://example.com/video2.png")
        fakeRepository.addVideos(video1, video2)

        val result = fakeRepository.getVideoResult().first()

        assertTrue(result is Result.Success)
        assertEquals(2, (result as Result.Success).data?.size)
        assertEquals(video1, result.data?.get(0))
        assertEquals(video2, result.data?.get(1))
    }

    @Test
    fun `getVideoResult should return error`() = runTest {
        fakeRepository.setShouldReturnError(true)

        val result = fakeRepository.getVideoResult().first()

        assertEquals("Fake error occurred", (result as Result.Error).message)
    }

    @Test
    fun `getVideoResult should simulate delay`() = runTest {
        fakeRepository.setSimulateDelay(true)

        val startTime = System.currentTimeMillis()
        fakeRepository.getVideoResult().first()
        val endTime = System.currentTimeMillis()

        assertTrue(endTime - startTime >= 1000)
    }
}