package me.kristianconk.bancash.presentation.features.movements

import android.icu.text.NumberFormat
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
import me.kristianconk.bancash.domain.model.Movement
import me.kristianconk.bancash.domain.model.MovementType
import me.kristianconk.bancash.presentation.utils.getTextForMovementType
import me.kristianconk.bancash.presentation.utils.getVectorForMovementType
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
