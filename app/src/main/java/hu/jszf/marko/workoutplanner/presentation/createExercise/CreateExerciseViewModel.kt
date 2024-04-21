package hu.jszf.marko.workoutplanner.presentation.createExercise

import androidx.lifecycle.ViewModel
import hu.jszf.marko.workoutplanner.db.repository.ExerciseRepository
import hu.jszf.marko.workoutplanner.model.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CreateExerciseViewModel(private val exerciseRepository: ExerciseRepository): ViewModel() {
    private var _exercise = MutableStateFlow<Exercise?>(null)
    val exercise: StateFlow<Exercise?> = _exercise

    suspend fun getExerciseById(id: Long?) {
        if (id == null) return

        val result = exerciseRepository.getById(id) ?: return

        _exercise.update { result }
    }

    suspend fun modify(exercise: Exercise): Boolean {
        return exerciseRepository.update(exercise)
    }

    suspend fun create(exercise: Exercise): Boolean {
        return exerciseRepository.save(exercise)
    }
}