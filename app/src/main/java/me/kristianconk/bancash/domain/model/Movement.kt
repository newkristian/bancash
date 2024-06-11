package me.kristianconk.bancash.domain.model

import java.time.LocalDateTime

/**
 * Representa un movimiento dentro de la cuenta del cliente
 */
data class Movement(
    val id: String,
    val dateTime: LocalDateTime,
    val amount: Double,
    val type: MovementType,
    val description: String
)

/**
 * tipos de movimiento en la cuenta
 */
enum class MovementType {
    PAYMENT,
    WITHDRAW,
    PURCHASE,
    REFUND,
    TRANSFER,
    UNKNOWN
}
