package hu.jszf.marko.workoutplanner

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import hu.jszf.marko.workoutplanner.db.WorkoutDatabase
import hu.jszf.marko.workoutplanner.db.repository.WorkoutActivityRepository
import hu.jszf.marko.workoutplanner.presentation.NavigatorViewModel
import hu.jszf.marko.workoutplanner.presentation.allActivity.AllActivityViewModel
import hu.jszf.marko.workoutplanner.presentation.workoutActivity.WorkoutActivityViewModel
import hu.jszf.marko.workoutplanner.presentation.viewModelFactory
import hu.jszf.marko.workoutplanner.ui.snackbar.SnacbarViewModel

interface AppModule {
    var globalVMStoreOwner: ViewModelStoreOwner
    val workoutDatabase: WorkoutDatabase
    val workoutActivityRepository: WorkoutActivityRepository
    val navigatorViewModelFactory: ViewModelProvider.Factory
    val workoutActivityViewModelFactory: ViewModelProvider.Factory

    @Composable
    fun getAllActivityViewModel(): AllActivityViewModel

    @Composable
    fun getSnackbarViewModel(): SnacbarViewModel
}

class AppModuleImpl(
    private val appContext: Context
): AppModule {
    override val workoutDatabase: WorkoutDatabase by lazy {
        Room.databaseBuilder(
            appContext.applicationContext,
            WorkoutDatabase::class.java,
            "workout"
        ).build()
    }

    override lateinit var globalVMStoreOwner: ViewModelStoreOwner

    override val workoutActivityRepository: WorkoutActivityRepository by lazy {
        WorkoutActivityRepository(workoutDatabase.workoutActivityDao())
    }

    override val navigatorViewModelFactory: ViewModelProvider.Factory by lazy {
        viewModelFactory { NavigatorViewModel }
    }

    override val workoutActivityViewModelFactory: ViewModelProvider.Factory
        get() = viewModelFactory { WorkoutActivityViewModel(workoutActivityRepository) }

    @Composable
    override fun getAllActivityViewModel(): AllActivityViewModel {
        return viewModel<AllActivityViewModel>(factory = viewModelFactory { AllActivityViewModel(workoutActivityRepository) })
    }

    @Composable
    override fun getSnackbarViewModel(): SnacbarViewModel {
        return viewModel<SnacbarViewModel>(viewModelStoreOwner = globalVMStoreOwner, factory = viewModelFactory { SnacbarViewModel() })
    }
}