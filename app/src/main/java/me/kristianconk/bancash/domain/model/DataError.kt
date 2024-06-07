package me.kristianconk.bancash.domain.model

sealed interface DataError : BancashError {
    enum class NetworkError : DataError {
        REQUEST_TIMEOUT,
        NO_INTERNET,
        SERVER_ERROR,
        BAD_REQUEST,
        UNKNOWN
    }

    enum class LocalError : DataError {
        IO,
        UNKNOWN,
    }
}