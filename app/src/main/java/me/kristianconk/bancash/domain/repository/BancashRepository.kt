package me.kristianconk.bancash.domain.repository

import android.net.Uri
import me.kristianconk.bancash.domain.model.BancashResult
import me.kristianconk.bancash.domain.model.DataError
import me.kristianconk.bancash.domain.model.Movement
import me.kristianconk.bancash.domain.model.User
import me.kristianconk.bancash.domain.model.UserBalance

interface BancashRepository {

    /**
     * Recupera el usuario actual en session o null si no hay ninguno
     *
     *
     */
    suspend fun getCurrentLoggedUser(): User?

    /**
     * Inicia sesion con usuario y password
     *
     * @param
     */
    suspend fun logIn(
        username: String,
        password: String
    ): BancashResult<User, DataError.NetworkError>

    /**
     * Registra un nuevo usuario
     *
     * @param email correo electronico
     * @param password contraseña
     * @param name nombre
     * @param lastName apellido
     * @param avatarUri uri de una foto del usuario (selfie)
     */
    suspend fun signUp(
        email: String,
        password: String,
        name: String,
        lastName: String,
        avatarUri: Uri
    ): BancashResult<User, DataError.NetworkError>

    /**
     * Cierra la sesion del usuario activo
     */
    suspend fun closeSession(): Boolean

    /**
     * Subir una foto al servidor y devuelve una Uri accesible para volver a descargar
     *
     * @param uri Uri de la foto a subir
     */
    suspend fun uploadPicture(uri: Uri): Uri

    /**
     * Recupera un objeto simple con información de la cuenta (balance)
     */
    suspend fun getBalance(): BancashResult<UserBalance, DataError.NetworkError>

    /**
     * Recupera un listado de los movimientos del usuario
     */
    suspend fun getMovements(): BancashResult<List<Movement>, DataError.NetworkError>
}