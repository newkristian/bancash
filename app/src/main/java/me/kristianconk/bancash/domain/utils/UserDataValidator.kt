package me.kristianconk.bancash.domain.utils

import java.util.regex.Pattern

/**
 * Clase de utilida para validar que un email o un password son correctos
 */
class UserDataValidator {

    private val emailPattern: Pattern = Pattern.compile(EMAIL_REGEX_PATTERN)
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

    fun isEmailValid(email: String): Boolean {
        return emailPattern.matcher(email).matches()
    }

    companion object {
        // basado en la recomendacion OWASP
        // https://owasp.org/www-community/OWASP_Validation_Regex_Repository
        const val EMAIL_REGEX_PATTERN = ("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
    }
}