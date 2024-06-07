package me.kristianconk.bancash.presentation.features.login

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.kristianconk.bancash.domain.usecases.LoginUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.kristianconk.bancash.data.repository.BancashRepositoryImp
import me.kristianconk.bancash.domain.utils.UserDataValidator

class LoginViewModel(
    val loginUseCase: LoginUseCase
) : ViewModel() {

    private var _user = ""
    private var _pass = ""
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onUserChanges(user: String) {
        _user = user
    }

    fun onPassChanges(pass: String) {
        _pass = pass
    }

    fun onLoginClick() {
        viewModelScope.launch {
            when (val res = loginUseCase.execute(_user, _pass)) {
                LoginUseCase.LoginResult.SUCCESS -> _uiState.update { it.copy(emailError = null, passwordError = null) }
                LoginUseCase.LoginResult.USERNAME_EMPTY -> _uiState.update { it.copy(emailError = "No puede ser vacío") }
                LoginUseCase.LoginResult.PASSWORD_EMPTY -> _uiState.update { it.copy(passwordError = "No puede ser vacío") }
                LoginUseCase.LoginResult.INVALID_INPUT -> _uiState.update { it.copy(emailError = "Datos incorrectos", passwordError = "Datos incorrectos") }
                LoginUseCase.LoginResult.EXTERNAL_ERROR -> _uiState.update { it.copy(message = "Hubo un problema intente de nuevo más tarde") }
            }
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                //val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                //val savedStateHandle = extras.createSavedStateHandle()

                return LoginViewModel(
                    LoginUseCase(
                        validator = UserDataValidator(),
                        repository = BancashRepositoryImp(FirebaseAuth.getInstance())
                    )
                ) as T
            }
        }
    }

}