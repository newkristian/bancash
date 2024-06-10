package me.kristianconk.bancash.presentation.features.signup

import android.net.Uri

data class SignupUiState(
    val emailError: String? = null,
    val passwordError: String? = null,
    val nameError: String? = null,
    val lastnameError: String? = null,
    val photoError: String? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)

data class SignupActions(
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onNameChange: (String) -> Unit = {},
    val onLastNameChange: (String) -> Unit = {},
    val onPhotoSelected: (Uri) -> Unit = {},
    val onSignupClick: () -> Unit = {},
    val onLoginClick: () -> Unit = {}
)
