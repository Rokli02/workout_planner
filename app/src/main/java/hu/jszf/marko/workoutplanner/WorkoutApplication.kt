package hu.jszf.marko.workoutplanner

import android.app.Application

class WorkoutApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }

    companion object {
        lateinit var appModule: AppModule
    }
}