package me.kristianconk.bancash.presentation.features.home

import me.kristianconk.bancash.domain.model.Movement

data class HomeUiState(
    val userName: String,
    val balance: Double? = null,
    val movements: List<Movement> = emptyList()
)

data class HomeActions(
    val onSignOut: () -> Unit = {},
    val onMovementClick: (Movement) -> Unit = {}
)