package hu.jszf.marko.workoutplanner.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

object NavigatorViewModel: ViewModel() {
    @SuppressLint("StaticFieldLeak")
    lateinit var navController: NavHostController
        private set

    fun updateNavController(navController: NavHostController) {
        this.navController = navController
    }
}