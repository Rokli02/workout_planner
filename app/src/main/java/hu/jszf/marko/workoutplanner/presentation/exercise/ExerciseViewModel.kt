package hu.jszf.marko.workoutplanner.presentation.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.jszf.marko.workoutplanner.db.repository.ExerciseRepository
import hu.jszf.marko.workoutplanner.model.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExerciseViewModel(private val exerciseRepository: ExerciseRepository): ViewModel() {
    private val _exercise = MutableStateFlow<Exercise?>(null)
    val exercise: StateFlow<Exercise?> = _exercise

    suspend fun getExercise(exerciseId: Long, activityId: Long?) {
        if (activityId != null) {
            _exercise.update { exerciseRepository.getByExerciseAndActivity(exerciseId, activityId) }

            return
        }

        exerciseRepository.getById(exerciseId)?.also {
            _exercise.update { it }
        }
    }

    fun updateExercise(newExercise: Exercise) {
        viewModelScope.launch {
            newExercise.activityExercise?.also { activityExercise ->
                exerciseRepository.updateActivityExercise(activityExercise).also { isUpdated ->
                    if (!isUpdated) return@launch

                    _exercise.update { newExercise }
                }
            }
        }
    }
}