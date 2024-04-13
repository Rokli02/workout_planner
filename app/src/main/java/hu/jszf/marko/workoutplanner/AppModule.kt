package hu.jszf.marko.workoutplanner

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import hu.jszf.marko.workoutplanner.db.WorkoutDatabase
import hu.jszf.marko.workoutplanner.db.repository.WorkoutActivityRepository
import hu.jszf.marko.workoutplanner.presentation.NavigatorViewModel
import hu.jszf.marko.workoutplanner.presentation.workoutActivity.WorkoutActivityViewModel
import hu.jszf.marko.workoutplanner.presentation.viewModelFactory

interface AppModule {
    val workoutDatabase: WorkoutDatabase
    val workoutActivityRepository: WorkoutActivityRepository
    val navigatorViewModelFactory: ViewModelProvider.Factory
    val workoutActivityViewModelFactory: ViewModelProvider.Factory
}

class AppModuleImpl(
    private val appContext: Context
): AppModule {
    override val workoutDatabase: WorkoutDatabase
        get() = WorkoutDatabase.getDatabase(appContext)

    override val workoutActivityRepository: WorkoutActivityRepository by lazy {
        WorkoutActivityRepository(workoutDatabase.workoutActivityDao())
    }

    override val navigatorViewModelFactory: ViewModelProvider.Factory by lazy {
        viewModelFactory { NavigatorViewModel }
    }

    override val workoutActivityViewModelFactory: ViewModelProvider.Factory
        get() = viewModelFactory { WorkoutActivityViewModel(workoutActivityRepository) }
}