package me.kristianconk.bancash.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import me.kristianconk.bancash.data.utils.ACCOUNTS_DB_COLLECTION
import me.kristianconk.bancash.data.utils.ACCOUNT_BALANCE
import me.kristianconk.bancash.data.utils.USERS_DB_COLLECTION
import me.kristianconk.bancash.data.utils.USER_AUTH_ID
import me.kristianconk.bancash.data.utils.USER_EMAIL
import me.kristianconk.bancash.data.utils.USER_LAST_NAME
import me.kristianconk.bancash.data.utils.USER_NAME
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

    override suspend fun getCurrentLoggedUser(): User? {
        return firebaseAuth.currentUser?.let {
            val userQuery =
                dbFirestore.collection(USERS_DB_COLLECTION).whereEqualTo(USER_AUTH_ID, it.uid)
            val result = userQuery.get().await()
            check(!result.isEmpty)
            val mapData = result.documents[0].data
            if (mapData != null) {
                User(
                    id = it.uid,
                    username = mapData[USER_NAME].toString(),
                    state = UserState.ACTIVE
                )
            } else null
        }
    }

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
                val user = hashMapOf(
                    USER_AUTH_ID to it.uid,
                    USER_EMAIL to email,
                    USER_NAME to name,
                    USER_LAST_NAME to lastName
                )
                dbFirestore.collection(USERS_DB_COLLECTION).add(user).await()
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
        } catch (ex: Exception) {
            Log.w("BACHAS-REPO", "error al crear usuario")
            return BancashResult.Error(DataError.NetworkError.UNKNOWN)
        }
    }

    override suspend fun getBalance(): BancashResult<UserBalance, DataError.NetworkError> {
        getCurrentLoggedUser()?.let {
            val accountQuery =
                dbFirestore.collection(ACCOUNTS_DB_COLLECTION).whereEqualTo(USER_AUTH_ID, it.id)
            val result = accountQuery.get().await()
            if (result.isEmpty) {
                // crear la cuenta y regalo bienvenida $100
                val account = hashMapOf(USER_AUTH_ID to it.id, ACCOUNT_BALANCE to 100.0)
                val doc = dbFirestore.collection(ACCOUNTS_DB_COLLECTION).document()
                doc.set(account).await()
                return BancashResult.Success(UserBalance(doc.id, 100.0))
            } else {
                // devolver cuenta con saldo acutal
                val firstDoc = result.documents[0]
                return BancashResult.Success(
                    UserBalance(
                        firstDoc.id,
                        firstDoc.data?.get(ACCOUNT_BALANCE)?.toString()?.toDoubleOrNull() ?: 0.0
                    )
                )
            }
        } ?: run {
            return BancashResult.Error(DataError.NetworkError.UNKNOWN)
        }
    }

    override suspend fun getMovements(): BancashResult<List<Movement>, DataError.NetworkError> {
        return BancashResult.Error(DataError.NetworkError.UNKNOWN)
    }
}