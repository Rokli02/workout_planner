package hu.jszf.marko.workoutplanner.presentation.createActivity

import androidx.lifecycle.ViewModel
import hu.jszf.marko.workoutplanner.db.repository.WorkoutActivityRepository
import hu.jszf.marko.workoutplanner.model.WorkoutActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CreateActivityViewModel(private val woActivityRepository: WorkoutActivityRepository): ViewModel() {
    val woActivity: MutableStateFlow<WorkoutActivity?> = MutableStateFlow(null)
    val isNew: MutableStateFlow<Boolean> = MutableStateFlow(true)

    suspend fun processId(id: Long?) {
        isNew.update { true }

        if (id != null && id >= 0) {
            woActivity.update { this.getById(id) }

            if (woActivity.value == null || !woActivity.value!!.isNew) {
                isNew.update { false }
            }
        }
    }

    private suspend fun getById(id: Long): WorkoutActivity? {
        return woActivityRepository.getById(id)
    }

    suspend fun create(woActivity: WorkoutActivity, removables: MutableList<Long>): Boolean {
        val saveResult = woActivityRepository.save(woActivity) ?: return false

        val removedCount = woActivityRepository.removeActivityExercise(removables.map { Pair(saveResult, it) })
        if (removedCount != removables.size) {
            println("Not all activityExercises got removed!")
        }

        return true
    }

    suspend fun update(woActivity: WorkoutActivity, removables: MutableList<Long>): Boolean{
        if (!woActivityRepository.update(woActivity)) return false

        val removedCount = woActivityRepository.removeActivityExercise(removables.map { Pair(woActivity.id!!, it) })
        if (removedCount != removables.size) {
            println("Not all activityExercises got removed!")
        }

        return true
    }

    suspend fun delete(id: Long): Boolean {
        return woActivityRepository.deleteById(id)
    }
}