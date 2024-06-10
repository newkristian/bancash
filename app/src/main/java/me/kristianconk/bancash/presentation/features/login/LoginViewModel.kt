package me.kristianconk.bancash.presentation.features.login

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.kristianconk.bancash.domain.usecases.LoginUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.kristianconk.bancash.presentation.events.BancashEvent
import me.kristianconk.bancash.presentation.utils.NAV_ACTIVITY_HOME

class LoginViewModel(
    val loginUseCase: LoginUseCase
) : ViewModel() {

    private var _user = ""
    private var _pass = ""
    private val _uiState = MutableStateFlow(LoginUiState())
    private val _sideEffects = MutableStateFlow(BancashEvent(""))
    val uiState = _uiState.asStateFlow()
    val sideEffects = _sideEffects.asStateFlow()

    fun onUserChanges(user: String) {
        _user = user
    }

    fun onPassChanges(pass: String) {
        _pass = pass
    }

    fun onLoginClick() {
        _uiState.update { LoginUiState(loading = true) }
        viewModelScope.launch {
            when (val res = loginUseCase.execute(_user.trim(), _pass.trim())) {
                LoginUseCase.LoginResult.SUCCESS -> {
                    _uiState.update { LoginUiState() }
                    _sideEffects.value = BancashEvent(NAV_ACTIVITY_HOME)
                }
                LoginUseCase.LoginResult.USERNAME_EMPTY -> _uiState.update { it.copy(loading = false, emailError = "No puede ser vacío") }
                LoginUseCase.LoginResult.PASSWORD_EMPTY -> _uiState.update { it.copy(loading = false, passwordError = "No puede ser vacío") }
                LoginUseCase.LoginResult.INVALID_INPUT -> _uiState.update { it.copy(loading = false, emailError = "Datos incorrectos", passwordError = "Datos incorrectos") }
                LoginUseCase.LoginResult.EXTERNAL_ERROR -> _uiState.update { it.copy(loading = false, message = "Hubo un problema intente de nuevo más tarde") }
                LoginUseCase.LoginResult.INVALID_EMAIL -> _uiState.update { it.copy(loading = false, emailError = "Debe ser un correo válido") }
            }
        }
    }
}