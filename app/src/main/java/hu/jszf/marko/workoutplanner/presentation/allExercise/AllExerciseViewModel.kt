package hu.jszf.marko.workoutplanner.presentation.allExercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import hu.jszf.marko.workoutplanner.db.repository.ExerciseRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class AllExerciseViewModel(private val exerciseRepository: ExerciseRepository): ViewModel() {
    private val _filter = MutableStateFlow("")
    val filter: StateFlow<String> = _filter

    fun updateFilter(text: String) {
        _filter.update { text }
    }

    val exercises = filter.debounce(timeoutMillis = 750).flatMapLatest { value ->
        exerciseRepository.getByFilterPaged(value).cachedIn(viewModelScope)
    }
}