package me.kristianconk.bancash.domain.usecases

import me.kristianconk.bancash.domain.model.BancashResult
import me.kristianconk.bancash.domain.model.Movement
import me.kristianconk.bancash.domain.repository.BancashRepository

/**
 * Caso de uso para recuperar el listado de movimientos del cliente
 */
class GetMovementsUseCase(
    val repository: BancashRepository
) {
    suspend fun execute(): List<Movement> {
        return when(val result = repository.getMovements()) {
            is BancashResult.Error -> emptyList()
            is BancashResult.Success -> result.data
        }
    }
}