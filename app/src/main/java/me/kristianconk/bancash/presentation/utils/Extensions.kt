package me.kristianconk.bancash.presentation.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import me.kristianconk.bancash.R
import me.kristianconk.bancash.domain.model.MovementType
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

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

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        //getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        externalCacheDir
    )
    return image
}


fun getVectorForMovementType(type: MovementType): Int {
    return when (type) {
        MovementType.PAYMENT -> R.drawable.payment
        MovementType.WITHDRAW -> R.drawable.withdraw
        MovementType.PURCHASE -> R.drawable.purchase
        MovementType.REFUND -> R.drawable.refund
        MovementType.TRANSFER -> R.drawable.transfer
        MovementType.UNKNOWN -> R.drawable.error
    }
}

fun getTextForMovementType(type: MovementType): String {
    return when (type) {
        MovementType.PAYMENT -> "Depósito"
        MovementType.WITHDRAW -> "Retiro"
        MovementType.PURCHASE -> "Compra"
        MovementType.REFUND -> "Reembolso"
        MovementType.TRANSFER -> "Transferencia"
        MovementType.UNKNOWN -> "Desconocido"
    }
}

fun getColorForMovementType(type: MovementType): Color {
    return when(type) {
        MovementType.PAYMENT -> Color.Green
        MovementType.WITHDRAW -> Color.Yellow
        MovementType.PURCHASE -> Color.Red
        MovementType.REFUND -> Color.Green
        MovementType.TRANSFER -> Color.Blue
        MovementType.UNKNOWN -> Color.Gray
    }
}