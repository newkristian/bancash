package me.kristianconk.bancash.domain.usecases

import android.net.Uri
import me.kristianconk.bancash.domain.repository.BancashRepository
import me.kristianconk.bancash.domain.utils.UserDataValidator

class SignUpUseCase(
    val repository: BancashRepository,
    val validator: UserDataValidator
) {
    suspend fun execute(email: String, password: String, name: String, lastName: String, photoUri: Uri) {
        //repository.signUp()
    }
}