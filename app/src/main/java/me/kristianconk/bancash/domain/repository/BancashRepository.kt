package me.kristianconk.bancash.domain.repository

import kotlinx.coroutines.flow.Flow
import me.kristianconk.bancash.domain.model.BancashResult
import me.kristianconk.bancash.domain.model.DataError
import me.kristianconk.bancash.domain.model.Movement
import me.kristianconk.bancash.domain.model.User
import me.kristianconk.bancash.domain.model.UserBalance

interface BancashRepository {
    suspend fun logIn(
        username: String,
        password: String
    ): BancashResult<User, DataError.NetworkError>

    suspend fun signUp(
        email: String,
        password: String,
        name: String,
        lastName: String
    ): BancashResult<User, DataError.NetworkError>

    suspend fun getBalance(): BancashResult<UserBalance, DataError.NetworkError>

    suspend fun getMovements(): BancashResult<List<Movement>, DataError.NetworkError>
}