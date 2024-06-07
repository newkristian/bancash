package me.kristianconk.bancash.domain.utils

import me.kristianconk.bancash.domain.model.BancashError
import me.kristianconk.bancash.domain.model.BancashResult
import me.kristianconk.bancash.domain.model.PasswordError

class UserDataValidator {
    fun validatePassword(password: String): BancashResult<Unit, BancashError> {
        if (password.length < PASSWORD_MIN_LENGTH)
            return BancashResult.Error(PasswordError.TOO_SHORT)
        val uppercase = password.any { it.isUpperCase() }
        if (!uppercase)
            return BancashResult.Error(PasswordError.NO_UPPERCASE)
        val number = password.any { it.isDigit() }
        if (!number)
            return BancashResult.Error(PasswordError.NO_DIGIT)
        return BancashResult.Success(Unit)
    }

    fun validateUser(username: String): BancashResult<Unit, BancashError> {

        return BancashResult.Success(Unit)
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}