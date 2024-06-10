package me.kristianconk.bancash.presentation.components

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.kristianconk.bancash.presentation.utils.createImageFile

import java.util.Objects


@Composable
fun ImagePicker(
    onImageSelected: (Uri) -> Unit,
    modifier: Modifier = Modifier,
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
            onImageSelected(uri)
            Log.d("CAMERA", "Success -> $success")
            Log.d("CAMERA", "Uri -> $uri")
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = modifier,
    ) {
        if (hasImage) {
            AsyncImage(
                model = ImageRequest.Builder(context).data(uri).build(),
                contentDescription = "selfie",
                contentScale = ContentScale.Inside
            )
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

