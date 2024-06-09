package me.kristianconk.bancash.presentation.features.splash

import android.window.SplashScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import me.kristianconk.bancash.R
import me.kristianconk.bancash.ui.theme.BanCashTheme

@Composable
fun SplashScreen(
    inSession: suspend () -> Boolean,
    navToHome: () -> Unit,
    navToLogin: () -> Unit
    ) {
    LaunchedEffect(key1 = Unit) {
        delay(1000L)
        if (inSession()) {
            navToHome()
        } else {
            navToLogin()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_card))
        LottieAnimation(composition)
        Text(text = "Bancash\nTu app financiera")

    }
}

@Preview
@Composable
fun SplashScreenPreview(modifier: Modifier = Modifier) {
    BanCashTheme {
        SplashScreen(inSession = {false}, navToHome = {}, navToLogin = {})
    }
}