package me.kristianconk.bancash.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.kristianconk.bancash.presentation.features.login.LoginActions
import me.kristianconk.bancash.presentation.features.login.LoginScreen
import me.kristianconk.bancash.presentation.features.login.LoginUiState
import me.kristianconk.bancash.presentation.features.login.LoginViewModel
import me.kristianconk.bancash.presentation.features.signup.SignupActions
import me.kristianconk.bancash.presentation.features.signup.SignupScreen
import me.kristianconk.bancash.presentation.features.signup.SignupViewModel

@Composable
fun BancashNavHos(
    loginViewModel: LoginViewModel,
    signupViewModel: SignupViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable(route = "login") {
            val loginState = loginViewModel.uiState.collectAsState().value
            LoginScreen(
                state = loginState,
                actions = LoginActions(
                    onEmailChange = loginViewModel::onUserChanges,
                    onPasswordChange = loginViewModel::onPassChanges,
                    onLoginClick = loginViewModel::onLoginClick,
                    onRegisterClick = {
                        navController.navigate(route = "register", navOptions = NavOptions.Builder().apply { setLaunchSingleTop(true) }.build())
                    }
                )
            )
        }
        composable(route = "register") {
            val signupState = signupViewModel.uiState.collectAsState().value
            SignupScreen(state = signupState, actions = SignupActions(
                onLoginClick = {
                    navController.navigate(route = "login", navOptions = NavOptions.Builder().apply { setLaunchSingleTop(true) }.build())
                }
            ))
        }
        composable(route = "home") {

        }
        composable(route = "movement-detail") {

        }
    }
}
