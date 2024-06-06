package me.kristianconk.bancash.domain.model

sealed interface DataError : BancashError {
    enum class NetworkError : DataError {
        REQUEST_TIMEOUT,
        NO_INTERNET,
        SERVER_ERROR,
        UNKNOWN
    }

    enum class LocalError : DataError {
        IO,
        UNKNOWN,
    }
}