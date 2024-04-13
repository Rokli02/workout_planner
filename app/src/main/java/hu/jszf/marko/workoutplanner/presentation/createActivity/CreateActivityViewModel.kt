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

            // isNew false || null
            if (woActivity.value == null || !woActivity.value!!.isNew) {
                isNew.update { false }
            }
        }
    }

    private suspend fun getById(id: Long): WorkoutActivity? {
        val result = woActivityRepository.getById(id)

        println("GetById: " + result.toString())

        return result
    }

    suspend fun create(woActivity: WorkoutActivity): Boolean {
        return woActivityRepository.save(woActivity)
    }

    suspend fun update(woActivity: WorkoutActivity): Boolean{
        return woActivityRepository.update(woActivity)
    }
}