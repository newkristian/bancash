package me.kristianconk.bancash.presentation.features.movements

import android.icu.text.NumberFormat
import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.kristianconk.bancash.R
import me.kristianconk.bancash.domain.model.Movement
import me.kristianconk.bancash.domain.model.MovementType
import me.kristianconk.bancash.ui.theme.BanCashTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MovementRow(movement: Movement, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = getVectorForMovementType(movement.type)),
            contentDescription = "retiro",
            modifier = Modifier.padding(8.dp)
        )
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(text = getTextForMovementType(movement.type))
            Text(text = movement.dateTime.format(DateTimeFormatter.ISO_DATE))
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = NumberFormat.getCurrencyInstance().format(movement.amount), modifier = Modifier.padding(8.dp))
    }
}

private fun getVectorForMovementType(type: MovementType): Int {
    return when (type) {
        MovementType.PAYMENT -> R.drawable.payment
        MovementType.WITHDRAW -> R.drawable.withdraw
        MovementType.PURCHASE -> R.drawable.purchase
        MovementType.REFUND -> R.drawable.refund
        MovementType.TRANSFER -> R.drawable.transfer
        MovementType.UNKNOWN -> R.drawable.error
    }
}

private fun getTextForMovementType(type: MovementType): String {
    return when (type) {
        MovementType.PAYMENT -> "Depósito"
        MovementType.WITHDRAW -> "Retiro"
        MovementType.PURCHASE -> "Compra"
        MovementType.REFUND -> "Reembolso"
        MovementType.TRANSFER -> "Transferencia"
        MovementType.UNKNOWN -> "Desconocido"
    }
}

@Preview
@Composable
fun MovementRowPreview(modifier: Modifier = Modifier) {
    BanCashTheme {
        MovementRow(
            movement = Movement(
                id = "123456",
                dateTime = LocalDateTime.now(),
                amount = 499.0,
                type = MovementType.WITHDRAW,
                description = "retiro en cajero"
            )
        )
    }
}