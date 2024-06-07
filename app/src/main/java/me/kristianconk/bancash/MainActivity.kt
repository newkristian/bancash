package me.kristianconk.bancash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import me.kristianconk.bancash.presentation.features.login.LoginActions
import me.kristianconk.bancash.presentation.features.login.LoginScreen
import me.kristianconk.bancash.presentation.features.login.LoginUiState
import me.kristianconk.bancash.presentation.features.login.LoginViewModel
import me.kristianconk.bancash.presentation.navigation.BancashNavHos
import me.kristianconk.bancash.ui.theme.BanCashTheme

class MainActivity : ComponentActivity() {

    private val loginViewModel : LoginViewModel by viewModels { LoginViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BanCashTheme {
                BancashNavHos(loginViewModel = loginViewModel)
            }
        }
    }
}
