package me.kristianconk.bancash.presentation.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.kristianconk.bancash.domain.model.Movement
import me.kristianconk.bancash.domain.repository.BancashRepository
import me.kristianconk.bancash.domain.usecases.GetBalanceUseCase
import me.kristianconk.bancash.domain.usecases.GetMovementsUseCase
import me.kristianconk.bancash.presentation.events.BancashEvent
import me.kristianconk.bancash.presentation.utils.NAVHOST_ROUTE_MOVEMENT_DETAIL
import me.kristianconk.bancash.presentation.utils.NAV_ACTIVITY_LOGIN

class HomeViewModel(
    val repository: BancashRepository,
    val balanceUseCase: GetBalanceUseCase,
    val movementsUseCase: GetMovementsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(userName = ""))
    val uiState = _uiState.asStateFlow()

    private val _sideEffects = MutableStateFlow(BancashEvent(""))
    val sideEffects = _sideEffects.asStateFlow()
    var selectedMovement: Movement? = null
        private set


    init {
        viewModelScope.launch {
            repository.getCurrentLoggedUser()?.let { user ->
                _uiState.update { it.copy(userName = user.username, avatarUrl = user.avatarUrl) }
            }
            balanceUseCase.execute()?.let { balance ->
                _uiState.update { it.copy(balance = balance.accountBalance) }
            }
            movementsUseCase.execute().run {
                _uiState.update { it.copy(movements = this) }
            }
        }
    }

    fun onMovementClick(movement: Movement) {
        viewModelScope.launch {
            selectedMovement = movement
            _sideEffects.value = BancashEvent(NAVHOST_ROUTE_MOVEMENT_DETAIL)
        }
    }

    fun onSignOut() {
        viewModelScope.launch {
            if (repository.closeSession()) {
                _sideEffects.value = BancashEvent(NAV_ACTIVITY_LOGIN)
            }
        }
    }
}