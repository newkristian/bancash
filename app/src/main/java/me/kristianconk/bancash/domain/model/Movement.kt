package me.kristianconk.bancash.domain.model

import java.time.LocalDateTime

data class Movement(
    val id: String,
    val dateTime: LocalDateTime,
    val amount: Double,
    val type: MovementType,
    val description: String
)

enum class MovementType {
    PAYMENT,
    WITHDRAW,
    PURCHASE,
    REFUND,
    TRANSFER,
    UNKNOWN
}
