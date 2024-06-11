package me.kristianconk.bancash.presentation.features.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import me.kristianconk.bancash.ui.theme.BanCashTheme

@Composable
fun SignupSuccessScreen(navToHome: () -> Unit) {
    LaunchedEffect(key1 = Unit) {
        delay(3000L)
        navToHome()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = "Tu cuenta ha sido creada exitosamente",
            modifier = Modifier.fillMaxWidth().align(Alignment.Center).padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview
@Composable
fun SignupSuccessScreenPreview(modifier: Modifier = Modifier) {
    BanCashTheme {
        SignupSuccessScreen {

        }
    }
}