package me.kristianconk.bancash.presentation.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.kristianconk.bancash.presentation.features.detail.MovementDetailScreen
import me.kristianconk.bancash.presentation.features.home.HomeActions
import me.kristianconk.bancash.presentation.features.home.HomeScreen
import me.kristianconk.bancash.presentation.features.home.HomeViewModel
import me.kristianconk.bancash.presentation.utils.NAVHOST_ROUTE_HOME
import me.kristianconk.bancash.presentation.utils.NAVHOST_ROUTE_MOVEMENT_DETAIL
import me.kristianconk.bancash.presentation.utils.NAV_ACTIVITY_LOGIN
import me.kristianconk.bancash.presentation.utils.NavUtils

@Composable
fun HomeNavHost(
    homeViewModel: HomeViewModel,
    activity: ComponentActivity
) {
    val navController = rememberNavController()
    val sideEffect = homeViewModel.sideEffects.collectAsState().value
    LaunchedEffect(key1 = sideEffect) {
        sideEffect.getIfNotConsumed()?.let {
            if ((it as String) == NAV_ACTIVITY_LOGIN) {
                NavUtils.navToLogin(activity)
            }
            if ((it as String) == NAVHOST_ROUTE_MOVEMENT_DETAIL) {
                navController.navigate(NAVHOST_ROUTE_MOVEMENT_DETAIL)
            }
        }
    }
    NavHost(navController = navController, startDestination = NAVHOST_ROUTE_HOME) {
        composable(route = NAVHOST_ROUTE_HOME) {
            val state = homeViewModel.uiState.collectAsState().value
            HomeScreen(
                uiState = state,
                actions = HomeActions(
                    onSignOut = homeViewModel::onSignOut,
                    onMovementClick = homeViewModel::onMovementClick
                )
            )
        }
        composable(route = NAVHOST_ROUTE_MOVEMENT_DETAIL) {
            homeViewModel.selectedMovement?.let { mov -> MovementDetailScreen(movement = mov) }
                ?: run {
                    navController.popBackStack()
                }
        }
    }
}