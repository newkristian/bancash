package me.kristianconk.bancash.domain.repository

import kotlinx.coroutines.flow.Flow
import me.kristianconk.bancash.domain.model.Movement
import me.kristianconk.bancash.domain.model.User
import me.kristianconk.bancash.domain.model.UserBalance

interface BancashRepository {
    fun logIn(): Flow<User>

    fun signUp(): Flow<User>

    fun getBalance(): Flow<UserBalance>

    fun getMovements(): Flow<List<Movement>>
}