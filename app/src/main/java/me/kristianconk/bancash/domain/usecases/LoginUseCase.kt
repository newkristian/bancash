package me.kristianconk.bancash.domain.usecases

import me.kristianconk.bancash.domain.model.BancashResult
import me.kristianconk.bancash.domain.model.DataError
import me.kristianconk.bancash.domain.repository.BancashRepository
import me.kristianconk.bancash.domain.utils.UserDataValidator

/**
 * Caso de uso de ingreso a la aplicacion, valida los campos de usuario/email y password
 */
class LoginUseCase(
    val validator: UserDataValidator,
    val repository: BancashRepository
) {
    suspend fun execute(username:String, password:String): LoginResult {
        if(username.isBlank()) {
            return LoginResult.USERNAME_EMPTY
        }
        if(password.isBlank()) {
            return LoginResult.PASSWORD_EMPTY
        }
        if(!validator.isEmailValid(username)) {
            return LoginResult.INVALID_EMAIL
        }
        return when(val repoResult = repository.logIn(username, password)) {
            is BancashResult.Error -> {
                when(repoResult.error) {
                    DataError.NetworkError.REQUEST_TIMEOUT -> LoginResult.EXTERNAL_ERROR
                    DataError.NetworkError.NO_INTERNET -> LoginResult.EXTERNAL_ERROR
                    DataError.NetworkError.SERVER_ERROR -> LoginResult.EXTERNAL_ERROR
                    DataError.NetworkError.UNKNOWN -> LoginResult.EXTERNAL_ERROR
                    DataError.NetworkError.BAD_REQUEST -> LoginResult.INVALID_INPUT
                }
            }

            is BancashResult.Success -> {
                LoginResult.SUCCESS
            }
        }

    }

    enum class LoginResult {
        SUCCESS,
        USERNAME_EMPTY,
        PASSWORD_EMPTY,
        INVALID_EMAIL,
        INVALID_INPUT, // cuando son credenciales incorrectas
        EXTERNAL_ERROR // casos como fallo de internet
    }
}