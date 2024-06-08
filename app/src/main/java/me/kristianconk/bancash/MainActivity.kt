package me.kristianconk.bancash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import me.kristianconk.bancash.presentation.navigation.BancashNavHos
import me.kristianconk.bancash.ui.theme.BanCashTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BanCashTheme {
                BancashNavHos(loginViewModel = koinViewModel(), signupViewModel = koinViewModel())
            }
        }
    }
}
