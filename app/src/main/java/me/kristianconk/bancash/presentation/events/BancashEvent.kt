package me.kristianconk.bancash.presentation.events

class BancashEvent<T>(val data: T) {

    private var consumed = false
    fun getIfNotConsumed(): Any? {
        if(!consumed) {
            consumed = true
            return data
        }
        return null
    }
}