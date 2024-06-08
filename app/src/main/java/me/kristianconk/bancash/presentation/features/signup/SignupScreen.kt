package me.kristianconk.bancash.presentation.features.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import me.kristianconk.bancash.ui.theme.BanCashTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    state: SignupUiState,
    actions: SignupActions,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Bancash",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
    ) { paddVals ->
        var name by remember {
            mutableStateOf("")
        }
        var lastName by remember {
            mutableStateOf("")
        }
        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        Box(
            modifier = Modifier
                .padding(paddVals)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(value = name, onValueChange = {
                    name = it
                    actions.onNameChange(it)
                }, label = { Text(text = "Nombre")},
                    isError = state.nameError != null,
                    supportingText = {
                        state.nameError?.let {
                            Text(text = it, color = MaterialTheme.colorScheme.error)
                        }
                    })
                OutlinedTextField(value = lastName, onValueChange = {
                    lastName = it
                    actions.onLastNameChange(it)
                }, label = { Text(text = "Apellido")},
                    isError = state.lastnameError != null,
                    supportingText = {
                        state.lastnameError?.let {
                            Text(text = it, color = MaterialTheme.colorScheme.error)
                        }
                    })
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
                    visualTransformation = PasswordVisualTransformation()
                )
                Button(onClick = {}) {
                    Text(text = "Tomar selfie")
                }
                Button(onClick = actions.onSignupClick) {
                    Text(text = "Registrar")
                }
                Button(onClick = actions.onLoginClick) {
                    Text(text = "Acceder")
                }
            }
        }
    }
}

@Preview
@Composable
fun SignupScreenPreview(modifier: Modifier = Modifier) {
    BanCashTheme {
        SignupScreen(state = SignupUiState(), actions = SignupActions())
    }
}