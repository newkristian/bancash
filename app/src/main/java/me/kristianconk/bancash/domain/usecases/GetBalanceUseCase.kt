package me.kristianconk.bancash.domain.usecases

import me.kristianconk.bancash.domain.model.BancashResult
import me.kristianconk.bancash.domain.model.UserBalance
import me.kristianconk.bancash.domain.repository.BancashRepository

/**
 * Caso de uso para recuperar el saldo de la cuenta del cliente
 */
class GetBalanceUseCase(
    val repository: BancashRepository
) {
    suspend fun execute(): UserBalance? {
        return when(val result = repository.getBalance()) {
            is BancashResult.Error -> null
            is BancashResult.Success -> result.data
        }
    }

}