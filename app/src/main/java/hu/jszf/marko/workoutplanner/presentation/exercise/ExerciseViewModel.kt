package hu.jszf.marko.workoutplanner.presentation.exercise

import androidx.lifecycle.ViewModel
import hu.jszf.marko.workoutplanner.db.repository.ExerciseRepository
import hu.jszf.marko.workoutplanner.model.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ExerciseViewModel(private val exerciseRepository: ExerciseRepository): ViewModel() {
    private val _exercise = MutableStateFlow<Exercise?>(null)
    val exercise: StateFlow<Exercise?> = _exercise

    suspend fun getExercise(exerciseId: Long, activityId: Long?) {
        if (activityId != null) {
            // TODO: Létrehozni a repo függvényeket, ami össze ollózzák

            return
        }

        exerciseRepository.getById(exerciseId)?.also {
            _exercise.update { it }
        }
    }
}