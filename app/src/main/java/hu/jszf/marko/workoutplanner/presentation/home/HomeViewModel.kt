package hu.jszf.marko.workoutplanner.presentation.home

import androidx.lifecycle.ViewModel
import hu.jszf.marko.workoutplanner.db.repository.WorkoutActivityRepository
import hu.jszf.marko.workoutplanner.model.WorkoutActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

private const val GetLastXWOActivity = 3

class HomeViewModel(private val activityRepository: WorkoutActivityRepository): ViewModel() {
    var lastActivities: MutableStateFlow<List<WorkoutActivity>> = MutableStateFlow(listOf())
        private set

    suspend fun getLastActivities() {
        lastActivities.update { activityRepository.getLast(GetLastXWOActivity) }
    }
}
