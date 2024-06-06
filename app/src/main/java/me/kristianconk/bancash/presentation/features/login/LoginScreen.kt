package me.kristianconk.bancash.presentation.features.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import me.kristianconk.bancash.ui.theme.BanCashTheme

@Composable
fun LoginScreen(
    state: LoginUiState,
    actions: LoginActions,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddVals ->
        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        Column(modifier = Modifier.padding(paddVals)) {
            OutlinedTextField(value = email, onValueChange = {
                email = it
                actions.onEmailChange(it)
            }, label = { Text(text = "Email") },
                isError = state.emailError != null,
                supportingText = {
                    state.emailError?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                })
            OutlinedTextField(value = password, onValueChange = {
                password = it
                actions.onPasswordChange(it)
            }, label = { Text(text = "Password") },
                isError = state.passwordError != null,
                supportingText = {
                    state.passwordError?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                },
                visualTransformation = PasswordVisualTransformation())
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview(modifier: Modifier = Modifier) {
    BanCashTheme {
        LoginScreen(state = LoginUiState(), actions = LoginActions())
    }
}