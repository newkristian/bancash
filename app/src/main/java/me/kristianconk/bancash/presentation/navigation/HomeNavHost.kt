package me.kristianconk.bancash.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.kristianconk.bancash.presentation.features.home.HomeActions
import me.kristianconk.bancash.presentation.features.home.HomeScreen
import me.kristianconk.bancash.presentation.features.home.HomeViewModel

@Composable
fun HomeNavHost(
    homeViewModel: HomeViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home") {
            val state = homeViewModel.uiState.collectAsState().value
            HomeScreen(
                uiState = state,
                actions = HomeActions(onMovementClick = homeViewModel::onMovementClick)
            )
        }
        composable(route = "movement-detail") {

        }
    }
}