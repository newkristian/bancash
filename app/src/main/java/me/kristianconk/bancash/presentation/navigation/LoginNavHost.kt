package me.kristianconk.bancash.presentation.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.kristianconk.bancash.presentation.components.LoadingScreen
import me.kristianconk.bancash.presentation.features.login.LoginActions
import me.kristianconk.bancash.presentation.features.login.LoginScreen
import me.kristianconk.bancash.presentation.features.login.LoginViewModel
import me.kristianconk.bancash.presentation.features.signup.SignupActions
import me.kristianconk.bancash.presentation.features.signup.SignupScreen
import me.kristianconk.bancash.presentation.features.signup.SignupSuccessScreen
import me.kristianconk.bancash.presentation.features.signup.SignupViewModel
import me.kristianconk.bancash.presentation.features.splash.SplashScreen
import me.kristianconk.bancash.presentation.features.splash.SplashViewModel
import me.kristianconk.bancash.presentation.utils.NAVHOST_ROUTE_LOGIN
import me.kristianconk.bancash.presentation.utils.NAVHOST_ROUTE_SIGNUP
import me.kristianconk.bancash.presentation.utils.NAVHOST_ROUTE_SPLASH
import me.kristianconk.bancash.presentation.utils.NAVHOST_ROUTE_SUCCESS
import me.kristianconk.bancash.presentation.utils.NAV_ACTIVITY_HOME
import me.kristianconk.bancash.presentation.utils.NavUtils

@Composable
fun LoginNavHost(
    loginViewModel: LoginViewModel,
    signupViewModel: SignupViewModel,
    splashViewModel: SplashViewModel,
    activity: ComponentActivity
) {
    val navController = rememberNavController()
    val sideEffectSignup = signupViewModel.sideEffects.collectAsState().value
    val sideEffectLogin = loginViewModel.sideEffects.collectAsState().value
    LaunchedEffect(key1 = sideEffectSignup) {
        sideEffectSignup.getIfNotConsumed()?.let {
            if ((it as String) == NAV_ACTIVITY_HOME) {
                navController.navigate(
                    route = NAVHOST_ROUTE_SUCCESS,
                    navOptions = NavOptions.Builder().apply { setLaunchSingleTop(true) }
                        .build()
                )
            }
        }
    }
    LaunchedEffect(key1 = sideEffectLogin) {
        sideEffectLogin.getIfNotConsumed()?.let {
            if ((it as String) == NAV_ACTIVITY_HOME) {
                NavUtils.navToHome(activity)
            }
        }
    }
    NavHost(navController = navController, startDestination = NAVHOST_ROUTE_SPLASH) {
        composable(route = NAVHOST_ROUTE_SPLASH) {
            SplashScreen(
                inSession = splashViewModel::existsUserInSession,
                navToHome = { NavUtils.navToHome(activity) },
                navToLogin = {
                    navController.navigate(
                        route = NAVHOST_ROUTE_LOGIN,
                        navOptions = NavOptions.Builder().apply { setLaunchSingleTop(true) }.build()
                    )
                })
        }
        composable(route = NAVHOST_ROUTE_LOGIN) {
            val loginState = loginViewModel.uiState.collectAsState().value
            if (loginState.loading) {
                LoadingScreen()
            } else {
                LoginScreen(
                    state = loginState,
                    actions = LoginActions(
                        onEmailChange = loginViewModel::onUserChanges,
                        onPasswordChange = loginViewModel::onPassChanges,
                        onLoginClick = loginViewModel::onLoginClick,
                        onRegisterClick = {
                            navController.navigate(
                                route = NAVHOST_ROUTE_SIGNUP,
                                navOptions = NavOptions.Builder().apply { setLaunchSingleTop(true) }
                                    .build()
                            )
                        }
                    )
                )
            }
        }
        composable(route = NAVHOST_ROUTE_SIGNUP) {
            val signupState = signupViewModel.uiState.collectAsState().value
            if(signupState.isLoading) {
                LoadingScreen()
            } else {
                SignupScreen(state = signupState, actions = SignupActions(
                    onEmailChange = signupViewModel::onEmailChanges,
                    onPasswordChange = signupViewModel::onPasswordChanges,
                    onNameChange = signupViewModel::onNameChanges,
                    onLastNameChange = signupViewModel::onLastNameChanges,
                    onPhotoSelected = signupViewModel::onPhotoSelected,
                    onSignupClick = signupViewModel::onSignupClick,
                    onLoginClick = {
                        navController.navigate(
                            route = NAVHOST_ROUTE_LOGIN,
                            navOptions = NavOptions.Builder().apply { setLaunchSingleTop(true) }
                                .build()
                        )
                    }
                ))
            }
        }
        composable(route = NAVHOST_ROUTE_SUCCESS) {
            SignupSuccessScreen {
                NavUtils.navToHome(activity)
            }
        }
    }
}
