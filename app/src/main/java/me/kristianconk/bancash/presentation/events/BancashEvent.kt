package me.kristianconk.bancash.presentation.events

sealed class BancashEvent {
    object  Empty: BancashEvent()
    data class NavigateTo(val destination: String): BancashEvent()
}