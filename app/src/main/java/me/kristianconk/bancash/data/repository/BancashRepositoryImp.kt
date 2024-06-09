package me.kristianconk.bancash.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import me.kristianconk.bancash.data.utils.ACCOUNTS_DB_COLLECTION
import me.kristianconk.bancash.data.utils.ACCOUNT_BALANCE
import me.kristianconk.bancash.data.utils.ACCOUNT_ID
import me.kristianconk.bancash.data.utils.MOVEMENTS_DB_COLLECTION
import me.kristianconk.bancash.data.utils.MOVEMENT_AMOUNT
import me.kristianconk.bancash.data.utils.MOVEMENT_DATETIME
import me.kristianconk.bancash.data.utils.MOVEMENT_DESCRIPTION
import me.kristianconk.bancash.data.utils.MOVEMENT_TYPE
import me.kristianconk.bancash.data.utils.USERS_DB_COLLECTION
import me.kristianconk.bancash.data.utils.USER_AUTH_ID
import me.kristianconk.bancash.data.utils.USER_EMAIL
import me.kristianconk.bancash.data.utils.USER_LAST_NAME
import me.kristianconk.bancash.data.utils.USER_NAME
import me.kristianconk.bancash.domain.model.BancashResult
import me.kristianconk.bancash.domain.model.DataError
import me.kristianconk.bancash.domain.model.Movement
import me.kristianconk.bancash.domain.model.MovementType
import me.kristianconk.bancash.domain.model.User
import me.kristianconk.bancash.domain.model.UserBalance
import me.kristianconk.bancash.domain.model.UserState
import me.kristianconk.bancash.domain.repository.BancashRepository
import java.time.LocalDateTime

class BancashRepositoryImp(
    val firebaseAuth: FirebaseAuth,
    val storage: FirebaseStorage,
    val dbFirestore: FirebaseFirestore
) : BancashRepository {

    private var cachedUser: User? = null

    override suspend fun getCurrentLoggedUser(): User? {
        if (cachedUser != null) {
            return cachedUser
        }
        return firebaseAuth.currentUser?.let { firebaseUser ->
            val userQuery =
                dbFirestore.collection(USERS_DB_COLLECTION)
                    .whereEqualTo(USER_AUTH_ID, firebaseUser.uid)
            val result = userQuery.get().await()
            check(!result.isEmpty)
            val mapData = result.documents[0].data
            if (mapData != null) {
                User(
                    id = firebaseUser.uid,
                    username = mapData[USER_NAME].toString(),
                    state = UserState.ACTIVE
                ).also {
                    cachedUser = it
                }
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

    override suspend fun closeSession(): Boolean {
        try {
            cachedUser = null
            firebaseAuth.currentUser?.delete()?.await()
            return true
        } catch (e: Exception){
            return false
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
                val movement = hashMapOf(
                    ACCOUNT_ID to doc.id,
                    MOVEMENT_TYPE to MovementType.PAYMENT.toString(),
                    MOVEMENT_AMOUNT to 100.0,
                    MOVEMENT_DESCRIPTION to "Promoción aplicada",
                    MOVEMENT_DATETIME to FieldValue.serverTimestamp()
                )
                dbFirestore.collection(MOVEMENTS_DB_COLLECTION).add(movement).await()
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
        when (val balance = getBalance()) {
            is BancashResult.Error -> return BancashResult.Error(balance.error)
            is BancashResult.Success -> {
                val movementsQuery = dbFirestore.collection(MOVEMENTS_DB_COLLECTION).whereEqualTo(
                    ACCOUNT_ID, balance.data.accountId
                )
                val result = movementsQuery.get().await()
                val listMovements = result.documents.map {
                    val data = it.data
                    Log.d("MOVEMENTS", data.toString())
                    Movement(
                        id = it.id,
                        dateTime = LocalDateTime.now(),
                        amount = data?.get(MOVEMENT_AMOUNT)?.toString()?.toDoubleOrNull() ?: 0.0,
                        type = MovementType.valueOf(
                            data?.get(MOVEMENT_TYPE)?.toString() ?: "UNKNOWN"
                        ),
                        description = data?.get(MOVEMENT_DESCRIPTION)?.toString() ?: ""
                    )
                }
                return BancashResult.Success(listMovements)
            }
        }
    }
}