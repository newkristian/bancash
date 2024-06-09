package me.kristianconk.bancash.presentation.utils

import android.content.Intent
import androidx.activity.ComponentActivity
import me.kristianconk.bancash.MainActivity
import me.kristianconk.bancash.presentation.features.home.HomeActivity

object NavUtils {
    fun navToHome(activity: ComponentActivity) {
        val i = Intent(activity, HomeActivity::class.java)
        activity.startActivity(i)
        activity.finish()
    }

    fun navToLogin(activity: ComponentActivity) {
        val i = Intent(activity, MainActivity::class.java)
        activity.startActivity(i)
        activity.finish()
    }
}