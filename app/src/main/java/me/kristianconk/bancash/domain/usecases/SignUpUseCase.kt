package me.kristianconk.bancash.domain.usecases

import android.net.Uri
import me.kristianconk.bancash.domain.model.BancashResult
import me.kristianconk.bancash.domain.model.DataError
import me.kristianconk.bancash.domain.repository.BancashRepository
import me.kristianconk.bancash.domain.utils.UserDataValidator

/**
 * Caso de uso de un registro de usuario nuevo, valida los campos de email password, nombre y apellido.
 * Verifica que se incluye una foto (selfie) pero no esta conectado a ningun motor de reconocimiento de rostros o datos asi cualquier foto pasa el filtro
 */
class SignUpUseCase(
    val repository: BancashRepository,
    val validator: UserDataValidator
) {
    suspend fun execute(email: String, password: String, name: String, lastName: String, photoUri: Uri?): SignupResult {

        if(name.isBlank()) {
            return SignupResult.NAME_EMPTY
        }
        if(lastName.isBlank()) {
            return SignupResult.LASTNAME_EMPTY
        }
        if(email.isBlank()) {
            return SignupResult.EMAIL_EMPTY
        }
        if(password.isBlank()) {
            return SignupResult.PASSWORD_EMPTY
        }
        if(!validator.isEmailValid(email)) {
            return SignupResult.INVALID_EMAIL
        }
        if(!validator.isValidPassword(password)){
            return SignupResult.INVALID_PASSWORD
        }
        if (photoUri == null) {
            return SignupResult.REQUIRES_PHOTO
        }
        return when(val repoRes = repository.signUp(email, password, name, lastName, photoUri)) {
            is BancashResult.Error -> {
                when(repoRes.error) {
                    DataError.NetworkError.REQUEST_TIMEOUT -> SignupResult.EXTERNAL_ERROR
                    DataError.NetworkError.NO_INTERNET -> SignupResult.EXTERNAL_ERROR
                    DataError.NetworkError.SERVER_ERROR -> SignupResult.EXTERNAL_ERROR
                    DataError.NetworkError.UNKNOWN -> SignupResult.EXTERNAL_ERROR
                    DataError.NetworkError.BAD_REQUEST -> SignupResult.INVALID_INPUT
                }
            }

            is BancashResult.Success -> {
                SignupResult.SUCCESS
            }
        }
    }

    enum class SignupResult {
        SUCCESS,
        EMAIL_EMPTY,
        PASSWORD_EMPTY,
        NAME_EMPTY,
        LASTNAME_EMPTY,
        INVALID_EMAIL,
        INVALID_PASSWORD,
        INVALID_INPUT,
        REQUIRES_PHOTO,
        EXTERNAL_ERROR
    }
}