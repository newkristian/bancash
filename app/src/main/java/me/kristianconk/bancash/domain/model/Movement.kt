package me.kristianconk.bancash.domain.model

import java.time.LocalDateTime

data class Movement(
    val dateTime: LocalDateTime,
    val amount: Double,
    val originAccount: String,
    val destinationAccount: String
)
