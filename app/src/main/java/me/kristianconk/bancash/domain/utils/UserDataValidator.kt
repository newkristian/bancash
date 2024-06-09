package me.kristianconk.bancash.domain.utils

import me.kristianconk.bancash.domain.model.BancashError
import me.kristianconk.bancash.domain.model.BancashResult

class UserDataValidator {
    fun isValidPassword(password: String): Boolean {
        if (password.length < PASSWORD_MIN_LENGTH)
            return false
        val uppercase = password.any { it.isUpperCase() }
        if (!uppercase)
            return false
        val number = password.any { it.isDigit() }
        if (!number)
            return false
        return true
    }

    fun validateUser(username: String): BancashResult<Unit, BancashError> {

        return BancashResult.Success(Unit)
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}