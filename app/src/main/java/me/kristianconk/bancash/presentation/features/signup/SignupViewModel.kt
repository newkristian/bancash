package me.kristianconk.bancash.presentation.features.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.kristianconk.bancash.domain.usecases.SignUpUseCase

class SignupViewModel(
    val useCase: SignUpUseCase
): ViewModel() {

    private val  _uiSate = MutableStateFlow(SignupUiState())
    val uiState = _uiSate.asStateFlow()


}