package me.kristianconk.bancash.domain.model

/**
 * Representa el saldo disponible en la cuenta del usuario
 */
data class UserBalance(
    val accountId: String,
    val accountBalance: Double
)
