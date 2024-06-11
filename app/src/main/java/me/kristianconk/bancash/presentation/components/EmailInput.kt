package me.kristianconk.bancash.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun EmailInput(
    email: String,
    emailError: String?,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = email, onValueChange = onValueChange, label = { Text(text = "Email") },
        isError = emailError != null,
        supportingText = {
            emailError?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}