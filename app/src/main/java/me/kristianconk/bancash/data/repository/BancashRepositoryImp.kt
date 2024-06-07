package me.kristianconk.bancash.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.tasks.await
import me.kristianconk.bancash.domain.model.BancashResult
import me.kristianconk.bancash.domain.model.DataError
import me.kristianconk.bancash.domain.model.Movement
import me.kristianconk.bancash.domain.model.User
import me.kristianconk.bancash.domain.model.UserBalance
import me.kristianconk.bancash.domain.model.UserState
import me.kristianconk.bancash.domain.repository.BancashRepository

class BancashRepositoryImp(
    val firebaseAuth: FirebaseAuth
) : BancashRepository {
    override suspend fun logIn(
        username: String,
        password: String
    ): BancashResult<User, DataError.NetworkError> {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(username, password).await()
            result.user?.let {
                return BancashResult.Success(
                    User(
                        id = it.uid,
                        username = it.displayName ?: "",
                        state = UserState.ACTIVE
                    )
                )
            } ?: run {
                return BancashResult.Error(DataError.NetworkError.UNKNOWN)
            }
        } catch (credentials: FirebaseAuthInvalidCredentialsException) {
            Log.w("BANCASH-REPO", "error credenciales", credentials)
            return BancashResult.Error(DataError.NetworkError.BAD_REQUEST)
        }
    }

    override suspend fun signUp(): BancashResult<User, DataError.NetworkError> {
        //firebaseAuth.createUserWithEmailAndPassword()
        return BancashResult.Error(DataError.NetworkError.UNKNOWN)
    }

    override suspend fun getBalance(): BancashResult<UserBalance, DataError.NetworkError> {
        return BancashResult.Error(DataError.NetworkError.UNKNOWN)
    }

    override suspend fun getMovements(): BancashResult<List<Movement>, DataError.NetworkError> {
        return BancashResult.Error(DataError.NetworkError.UNKNOWN)
    }
}