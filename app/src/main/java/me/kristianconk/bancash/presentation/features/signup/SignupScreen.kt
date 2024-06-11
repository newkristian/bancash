package me.kristianconk.bancash.presentation.features.signup

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.kristianconk.bancash.presentation.components.EmailInput
import me.kristianconk.bancash.presentation.components.ImagePicker
import me.kristianconk.bancash.presentation.components.PasswordInput
import me.kristianconk.bancash.presentation.utils.ConnectionState
import me.kristianconk.bancash.presentation.utils.connectivityState
import me.kristianconk.bancash.ui.theme.BanCashTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun SignupScreen(
    state: SignupUiState,
    actions: SignupActions,
    modifier: Modifier = Modifier
) {
    BackHandler(enabled = true) {
        // no esta permitido el back
    }
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = state.message) {
        state.message?.let {
            if (it.isNotEmpty())
                snackbarHostState.showSnackbar(message = it)
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
                EmailInput(email = email, emailError = state.emailError, onValueChange = {
                    email = it
                    actions.onEmailChange(it)
                })
                PasswordInput(
                    password = password,
                    passwordError = state.passwordError,
                    onValueChange = {
                        password = it
                        actions.onPasswordChange(it)
                    }
                )
                ImagePicker(onImageSelected = actions.onPhotoSelected, modifier = Modifier.size(width = 180.dp, height = 180.dp), errorMessage = state.photoError)
                Button(onClick = actions.onSignupClick, enabled = isConnected) {
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