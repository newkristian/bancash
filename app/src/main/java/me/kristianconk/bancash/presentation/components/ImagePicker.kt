package me.kristianconk.bancash.presentation.components

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.kristianconk.bancash.R
import me.kristianconk.bancash.presentation.utils.createImageFile

import java.util.Objects


/**
 * Composable para un seleccionador de foto, internamente lanza un Intent a la camara
 *
 * @param onImageSelected callback que se invoca con el Uri de la foto tomada
 */
@Composable
fun ImagePicker(
    onImageSelected: (Uri) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null
) {
    val context = LocalContext.current
    val uri by remember {
        mutableStateOf(
            FileProvider.getUriForFile(
                Objects.requireNonNull(context),
                "me.kristianconk.bancash.provider", context.createImageFile()
            )
        )
    }
    var hasImage by remember {
        mutableStateOf(false)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
            if (success) {
                onImageSelected(uri)
                Log.d("CAMERA", "Uri -> $uri")
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Es necesario el permiso de camara", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = modifier.border(BorderStroke(2.dp, MaterialTheme.colorScheme.primary)),
    ) {
        if (hasImage) {
            AsyncImage(
                model = ImageRequest.Builder(context).data(uri).build(),
                contentDescription = "selfie",
                contentScale = ContentScale.Inside
            )
        } else {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.empty_avatar),
                contentDescription = "avatar",
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Center)
            )
        }
        errorMessage?.let {
            Text(text = it, modifier = Modifier.align(Alignment.TopCenter))
        }
        Button(onClick = {
            Log.d("CAMERA", "Uri -> $uri")
            val permissionCheckResult =
                ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                cameraLauncher.launch(uri)
            } else {
                // Request a permission
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }, modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(text = "Selfie")
        }
    }
}

