package me.kristianconk.bancash.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.kristianconk.bancash.presentation.features.login.LoginActions
import me.kristianconk.bancash.presentation.features.login.LoginScreen
import me.kristianconk.bancash.presentation.features.login.LoginUiState
import me.kristianconk.bancash.presentation.features.login.LoginViewModel

@Composable
fun BancashNavHos(modifier: Modifier = Modifier, loginViewModel: LoginViewModel) {
    NavHost(navController = rememberNavController(), startDestination = "login") {
        composable(route = "login") {
            val loginState = loginViewModel.uiState.collectAsState().value
            LoginScreen(
                state = loginState,
                actions = LoginActions(
                    onEmailChange = loginViewModel::onUserChanges,
                    onPasswordChange = loginViewModel::onPassChanges,
                    onLoginClick = loginViewModel::onLoginClick
                )
            )
        }
    }
}
