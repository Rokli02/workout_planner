package hu.jszf.marko.workoutplanner.presentation.workoutActivity

import androidx.lifecycle.ViewModel
import hu.jszf.marko.workoutplanner.db.repository.WorkoutActivityRepository
import hu.jszf.marko.workoutplanner.model.WorkoutActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class WorkoutActivityViewModel(private val workoutActivityRepository: WorkoutActivityRepository): ViewModel() {
    val workoutActivity: MutableStateFlow<WorkoutActivity?> = MutableStateFlow(null)

    suspend fun getById(id: Long) {
        workoutActivity.update { workoutActivityRepository.getById(id) }
    }

    suspend fun getTodaysActivity(): WorkoutActivity? {
        val result = workoutActivityRepository.getToday()

        return result
    }
}