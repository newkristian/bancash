package me.kristianconk.bancash.presentation.features.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import me.kristianconk.bancash.presentation.utils.ConnectionState
import me.kristianconk.bancash.presentation.utils.connectivityState
import me.kristianconk.bancash.ui.theme.BanCashTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun LoginScreen(
    state: LoginUiState,
    actions: LoginActions,
    modifier: Modifier = Modifier
) {
    BackHandler(enabled = true) {
        // no esta permitido el back
    }
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = state.message) {
        if (state.message.isNotEmpty()) {
            snackbarHostState.showSnackbar(message = state.message)
        }
    }
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
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
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddVals ->
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
                Button(onClick = actions.onLoginClick, enabled = isConnected) {
                    Text(text = "Login")
                }
                Button(onClick = actions.onRegisterClick) {
                    Text(text = "Registro")
                }
            }
            if (!isConnected) {
                Text(
                    text = "No hay conexión a internet",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .align(Alignment.TopCenter)
                        .background(
                            Color.Red
                        ),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
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