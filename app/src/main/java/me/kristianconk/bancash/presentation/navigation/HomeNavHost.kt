package me.kristianconk.bancash.presentation.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.kristianconk.bancash.presentation.events.BancashEvent
import me.kristianconk.bancash.presentation.features.home.HomeActions
import me.kristianconk.bancash.presentation.features.home.HomeScreen
import me.kristianconk.bancash.presentation.features.home.HomeViewModel
import me.kristianconk.bancash.presentation.utils.NAVHOST_ROUTE_HOME
import me.kristianconk.bancash.presentation.utils.NAVHOST_ROUTE_MOVEMENT_DETAIL
import me.kristianconk.bancash.presentation.utils.NAV_ACTIVITY_LOGIN
import me.kristianconk.bancash.presentation.utils.NavUtils
import me.kristianconk.bancash.presentation.utils.observeWithLifecycle

@Composable
fun HomeNavHost(
    homeViewModel: HomeViewModel,
    activity: ComponentActivity
) {
    val navController = rememberNavController()
    homeViewModel.sideEffects.observeWithLifecycle {
        if (it is BancashEvent.NavigateTo){
            if( it.destination == NAV_ACTIVITY_LOGIN) {
                NavUtils.navToLogin(activity)
            }
            if(it.destination == NAVHOST_ROUTE_MOVEMENT_DETAIL) {
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
                    onMovementClick = homeViewModel::onMovementClick)
            )
        }
        composable(route = NAVHOST_ROUTE_MOVEMENT_DETAIL) {

        }
    }
}