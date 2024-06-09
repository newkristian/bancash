package me.kristianconk.bancash.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import me.kristianconk.bancash.domain.model.BancashResult
import me.kristianconk.bancash.domain.model.DataError
import me.kristianconk.bancash.domain.model.Movement
import me.kristianconk.bancash.domain.model.User
import me.kristianconk.bancash.domain.model.UserBalance
import me.kristianconk.bancash.domain.model.UserState
import me.kristianconk.bancash.domain.repository.BancashRepository

class BancashRepositoryImp(
    val firebaseAuth: FirebaseAuth,
    val storage: FirebaseStorage,
    val dbFirestore: FirebaseFirestore
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

    override suspend fun signUp(
        email: String,
        password: String,
        name: String,
        lastName: String
    ): BancashResult<User, DataError.NetworkError> {
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let {
                val user = hashMapOf("authId" to it.uid, "email" to email, "name" to name, "lastName" to lastName)
                dbFirestore.collection("users").add(user).await()
                return BancashResult.Success(User(id = it.uid, username = it.displayName ?: "", state = UserState.ACTIVE))
            } ?: run {
                return BancashResult.Error(DataError.NetworkError.UNKNOWN)
            }
        } catch (ex:Exception){
            Log.w("BACHAS-REPO", "error al crear usuario")
            return BancashResult.Error(DataError.NetworkError.UNKNOWN)
        }
    }

    override suspend fun getBalance(): BancashResult<UserBalance, DataError.NetworkError> {
        return BancashResult.Error(DataError.NetworkError.UNKNOWN)
    }

    override suspend fun getMovements(): BancashResult<List<Movement>, DataError.NetworkError> {
        return BancashResult.Error(DataError.NetworkError.UNKNOWN)
    }
}