package me.kristianconk.bancash

import android.app.Application
import com.google.firebase.FirebaseApp
import me.kristianconk.bancash.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BancashApp: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@BancashApp)
            modules(appModule)
        }
    }
}