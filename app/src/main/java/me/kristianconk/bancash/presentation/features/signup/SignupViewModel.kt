package me.kristianconk.bancash.presentation.features.signup

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.kristianconk.bancash.domain.usecases.SignUpUseCase
import me.kristianconk.bancash.presentation.events.BancashEvent
import me.kristianconk.bancash.presentation.utils.NAV_ACTIVITY_HOME

class SignupViewModel(
    val useCase: SignUpUseCase
) : ViewModel() {
    private var _name: String = ""
    private var _lastName: String = ""
    private var _email: String = ""
    private var _password: String = ""
    private var _photo: Uri? = null
    private val _uiState = MutableStateFlow(SignupUiState())
    private val _sideEffects = MutableStateFlow(BancashEvent(""))
    val uiState = _uiState.asStateFlow()
    val sideEffects = _sideEffects.asStateFlow()

    fun onNameChanges(name: String) {
        _name = name
    }

    fun onLastNameChanges(lastName: String) {
        _lastName = lastName
    }

    fun onEmailChanges(email: String) {
        _email = email
    }

    fun onPasswordChanges(password: String) {
        _password = password
    }

    fun onPhotoSelected(photoUri: Uri) {
        _photo = photoUri
        _uiState.update { it.copy(photoError = null) }
    }

    fun onSignupClick() {
        viewModelScope.launch {
            _uiState.value = SignupUiState(isLoading = true)
            val result = useCase.execute(_email, _password, _name, _lastName, _photo)
            when (result) {
                SignUpUseCase.SignupResult.SUCCESS -> {
                    _uiState.value = SignupUiState()
                    _sideEffects.value = BancashEvent(NAV_ACTIVITY_HOME)
                }
                SignUpUseCase.SignupResult.EMAIL_EMPTY -> _uiState.update { it.copy(isLoading = false, emailError = "No puede ser vacío") }
                SignUpUseCase.SignupResult.PASSWORD_EMPTY -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        passwordError = "No puede ser vacío"
                    )
                }
                SignUpUseCase.SignupResult.NAME_EMPTY -> _uiState.update { it.copy(isLoading = false, nameError = "No puede ser vacío") }
                SignUpUseCase.SignupResult.LASTNAME_EMPTY -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        lastnameError = "No puede ser vacío"
                    )
                }
                SignUpUseCase.SignupResult.INVALID_EMAIL -> _uiState.update { it.copy(isLoading = false, emailError = "El correo debe tener el formato correcto") }
                SignUpUseCase.SignupResult.INVALID_PASSWORD -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        passwordError = "mayusculas, minusculas, numero, 8 digitos"
                    )
                }
                SignUpUseCase.SignupResult.INVALID_INPUT -> _uiState.update { it.copy(isLoading = false, message = "Los datos no son válidos") }
                SignUpUseCase.SignupResult.REQUIRES_PHOTO -> _uiState.update { it.copy(isLoading = false, photoError = "Se necesita una foto selfie") }
                SignUpUseCase.SignupResult.EXTERNAL_ERROR -> _uiState.update { it.copy(isLoading = false, message = "Hubo un error intente de nuevo más tarde") }
            }
        }
    }
}