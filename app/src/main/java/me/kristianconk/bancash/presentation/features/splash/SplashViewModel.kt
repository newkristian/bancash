package me.kristianconk.bancash.presentation.features.splash

import androidx.lifecycle.ViewModel
import me.kristianconk.bancash.domain.repository.BancashRepository

class SplashViewModel(
    val repository: BancashRepository
) : ViewModel() {
    suspend fun existsUserInSession(): Boolean {
        return repository.getCurrentLoggedUser() != null
    }
}