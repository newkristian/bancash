package me.kristianconk.bancash.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import me.kristianconk.bancash.data.repository.BancashRepositoryImp
import me.kristianconk.bancash.domain.repository.BancashRepository
import me.kristianconk.bancash.domain.usecases.GetBalanceUseCase
import me.kristianconk.bancash.domain.usecases.GetMovementsUseCase
import me.kristianconk.bancash.domain.usecases.LoginUseCase
import me.kristianconk.bancash.domain.usecases.SignUpUseCase
import me.kristianconk.bancash.domain.utils.UserDataValidator
import me.kristianconk.bancash.presentation.features.home.HomeViewModel
import me.kristianconk.bancash.presentation.features.login.LoginViewModel
import me.kristianconk.bancash.presentation.features.signup.SignupViewModel
import me.kristianconk.bancash.presentation.features.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<BancashRepository> {
        BancashRepositoryImp(
            get(),
            FirebaseAuth.getInstance(),
            FirebaseStorage.getInstance(),
            FirebaseFirestore.getInstance()
        )
    }
    factory { UserDataValidator() }
    factory { LoginUseCase(get(), get()) }
    factory { SignUpUseCase(get(), get()) }
    factory { GetBalanceUseCase(get()) }
    factory { GetMovementsUseCase(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
}