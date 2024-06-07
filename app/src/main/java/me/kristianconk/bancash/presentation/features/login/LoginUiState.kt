package me.kristianconk.bancash.presentation.features.login

data class LoginUiState(
    val emailError: String? = null,
    val passwordError: String? = null,
    val loading: Boolean = false,
    val message: String = ""
)

data class LoginActions(
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onLoginClick: () -> Unit = {},
    val onRegisterClick: () -> Unit = {}
)