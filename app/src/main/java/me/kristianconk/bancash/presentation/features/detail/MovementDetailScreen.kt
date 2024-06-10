package me.kristianconk.bancash.presentation.features.detail

import android.icu.text.NumberFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.kristianconk.bancash.domain.model.Movement
import me.kristianconk.bancash.domain.model.MovementType
import me.kristianconk.bancash.presentation.utils.getColorForMovementType
import me.kristianconk.bancash.presentation.utils.getTextForMovementType
import me.kristianconk.bancash.presentation.utils.getVectorForMovementType
import me.kristianconk.bancash.ui.theme.BanCashTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MovementDetailScreen(movement: Movement) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(getColorForMovementType(movement.type))
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = getVectorForMovementType(movement.type)),
                contentDescription = "avatar",
                modifier = Modifier
                    .padding(16.dp)
                    .size(width = 100.dp, height = 100.dp)
                    .align(Alignment.Center)
            )
            Text(text = getTextForMovementType(movement.type), modifier = Modifier.align(Alignment.BottomCenter), style = MaterialTheme.typography.titleLarge)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Fecha")
            Text(text = movement.dateTime.format(DateTimeFormatter.ISO_DATE))
        }
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Hora")
            Text(text = movement.dateTime.format(DateTimeFormatter.ISO_TIME).substringBefore('.'))
        }
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Descipción")
            Text(text = movement.description)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Monto")
            Text(text = NumberFormat.getCurrencyInstance().format(movement.amount))
        }
    }
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