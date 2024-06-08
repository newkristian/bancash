package me.kristianconk.bancash.presentation.features.signup

data class SignupUiState(
    val emailError: String? = null,
    val passwordError: String? = null,
    val nameError: String? = null,
    val lastnameError: String? = null
)

data class SignupActions(
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onNameChange: (String) -> Unit = {},
    val onLastNameChange: (String) -> Unit = {},
    val onPhotoClick: () -> Unit = {},
    val onSignupClick: () -> Unit = {},
    val onLoginClick: () -> Unit = {}
)