package hu.jszf.marko.workoutplanner.presentation.home

import androidx.lifecycle.ViewModel
import hu.jszf.marko.workoutplanner.db.repository.WorkoutActivityRepository
import hu.jszf.marko.workoutplanner.model.WorkoutActivity
import hu.jszf.marko.workoutplanner.ui.theme.LogoLevelsRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar

internal const val GetLastXWOActivity = 3

class HomeViewModel(private val activityRepository: WorkoutActivityRepository): ViewModel() {
    var lastActivities: MutableStateFlow<List<WorkoutActivity>?> = MutableStateFlow(null)
        private set

    var logoLevelIndex: MutableStateFlow<Int> = MutableStateFlow(0)
        private set

    suspend fun getLastActivities() {
        val last = activityRepository.getLast(GetLastXWOActivity)
        var activitiesInTheLastWeek = 0
        val today = Calendar.getInstance().also {
            it.add(Calendar.DAY_OF_MONTH, -7)
        }

        last.forEach {
            if (today.before(it.date)) {
                activitiesInTheLastWeek++
            }
        }

        val index = when {
            activitiesInTheLastWeek < LogoLevelsRes.size -> activitiesInTheLastWeek
            else -> LogoLevelsRes.size - 1
        }

        logoLevelIndex.update { index }
        lastActivities.update { last }
    }
}
