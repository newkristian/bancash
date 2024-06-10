package me.kristianconk.bancash.presentation.features.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.kristianconk.bancash.domain.model.Movement
import me.kristianconk.bancash.domain.model.MovementType
import me.kristianconk.bancash.ui.theme.BanCashTheme
import java.time.LocalDateTime

@Composable
fun MovementDetailScreen(movement: Movement) {

}

@Preview
@Composable
fun MovementDetailScreenPreview(modifier: Modifier = Modifier) {
    BanCashTheme {
        MovementDetailScreen(
            movement = Movement(
                id = "12345",
                dateTime = LocalDateTime.now(),
                amount = 223389.99,
                MovementType.PURCHASE,
                description = "Compras de amazon"
            )
        )
    }
}