package me.kristianconk.bancash.presentation.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.kristianconk.bancash.domain.model.Movement
import me.kristianconk.bancash.domain.repository.BancashRepository
import me.kristianconk.bancash.domain.usecases.GetBalanceUseCase
import me.kristianconk.bancash.domain.usecases.GetMovementsUseCase

class HomeViewModel(
    repository: BancashRepository,
    balanceUseCase: GetBalanceUseCase,
    movementsUseCase: GetMovementsUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(userName = ""))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getCurrentLoggedUser()?.let { user ->
                _uiState.update { it.copy(userName = user.username) }
            }
            balanceUseCase.execute()?.let{ balance ->
                _uiState.update { it.copy(balance = balance.accountBalance) }
            }
        }
    }

    fun onMovementClick(movement: Movement) {

    }
}