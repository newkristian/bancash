package me.kristianconk.bancash.presentation.navigation

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import me.kristianconk.bancash.presentation.events.BancashEvent
import me.kristianconk.bancash.presentation.features.home.HomeActivity
import me.kristianconk.bancash.presentation.features.login.LoginActions
import me.kristianconk.bancash.presentation.features.login.LoginScreen
import me.kristianconk.bancash.presentation.features.login.LoginViewModel
import me.kristianconk.bancash.presentation.features.signup.SignupActions
import me.kristianconk.bancash.presentation.features.signup.SignupScreen
import me.kristianconk.bancash.presentation.features.signup.SignupViewModel

@Composable
fun LoginNavHost(
    loginViewModel: LoginViewModel,
    signupViewModel: SignupViewModel,
    activity: ComponentActivity
) {
    val navController = rememberNavController()
    signupViewModel.sideEffects.observeWithLifecycle {
        if (it is BancashEvent.NavigateTo && it.destination == "HOME") {
            val i = Intent(activity, HomeActivity::class.java)
            activity.startActivity(i)
            activity.finish()
        }
    }

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
                        navController.navigate(
                            route = "register",
                            navOptions = NavOptions.Builder().apply { setLaunchSingleTop(true) }
                                .build()
                        )
                    }
                )
            )
        }
        composable(route = "register") {
            val signupState = signupViewModel.uiState.collectAsState().value
            SignupScreen(state = signupState, actions = SignupActions(
                onEmailChange = signupViewModel::onEmailChanges,
                onPasswordChange = signupViewModel::onPasswordChanges,
                onNameChange = signupViewModel::onNameChanges,
                onLastNameChange = signupViewModel::onLastNameChanges,
                onSignupClick = signupViewModel::onSignupClick,
                onLoginClick = {
                    navController.navigate(
                        route = "login",
                        navOptions = NavOptions.Builder().apply { setLaunchSingleTop(true) }.build()
                    )
                }
            ))
        }
    }
}

@Composable
inline fun <reified T> Flow<T>.observeWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.lifecycleScope.launch {
            flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect(action)
        }
    }
}
