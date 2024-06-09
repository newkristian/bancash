package me.kristianconk.bancash.presentation.features.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import me.kristianconk.bancash.presentation.navigation.HomeNavHost
import me.kristianconk.bancash.ui.theme.BanCashTheme
import org.koin.androidx.compose.koinViewModel

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BanCashTheme {
                HomeNavHost(
                    homeViewModel = koinViewModel()
                )
            }
        }
    }
}