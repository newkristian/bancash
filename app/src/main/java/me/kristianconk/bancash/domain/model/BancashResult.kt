package me.kristianconk.bancash.domain.model


sealed interface BancashResult<out D, out E: BancashError> {
    data class Success<out D, out E: BancashError>(val data: D) : BancashResult<D,E>

    data class Error<out D, out E: BancashError>(val error: E) : BancashResult<D,E>

}