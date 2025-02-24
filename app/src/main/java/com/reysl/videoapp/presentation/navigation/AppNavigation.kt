package com.reysl.videoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reysl.videoapp.constants.PlayerConstants
import com.reysl.videoapp.domain.model.VideoItem
import com.reysl.videoapp.presentation.details_screen.DetailsScreen
import com.reysl.videoapp.presentation.details_screen.DetailsViewModel
import com.reysl.videoapp.presentation.main_screen.MainScreen
import com.reysl.videoapp.presentation.main_screen.MainViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()
    val detailsViewModel: DetailsViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = PlayerConstants.NAV_ROUTE_MAIN
    ) {
        composable(
            PlayerConstants.NAV_ROUTE_MAIN
        ) {
            MainScreen(mainViewModel, navigateToDetails = {
                navigateToDetails(navController, it)
            })
        }

        composable(
            PlayerConstants.NAV_ROUTE_DETAIL
        ) {
            navController.previousBackStackEntry?.savedStateHandle?.get<VideoItem?>(
                "videoItem"
            )?.let { videoItem ->
                DetailsScreen(
                    video = videoItem,
                    viewModel = detailsViewModel
                )
            }
        }
    }
}

private fun navigateToDetails(navController: NavController, video: VideoItem) {
    navController.currentBackStackEntry?.savedStateHandle?.set("videoItem", video)
    navController.navigate(
        route = PlayerConstants.NAV_ROUTE_DETAIL
    )
}