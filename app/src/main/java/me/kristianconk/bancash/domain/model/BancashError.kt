package me.kristianconk.bancash.domain.model

sealed interface BancashError

enum class PasswordError: BancashError {
    TOO_SHORT,
    NO_UPPERCASE,
    NO_DIGIT,
    NO_CHAR,
    NO_SPECIAL_CHAR,
    EMPTY
}

enum class UsernameError: BancashError {
    EMPTY
}

enum class EmailError : BancashError {
    EMPTY
}



