package hu.jszf.marko.workoutplanner.presentation.createActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import hu.jszf.marko.workoutplanner.db.repository.ExerciseRepository
import hu.jszf.marko.workoutplanner.model.Exercise
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class ExerciseSelectorViewModel(private val exerciseRepository: ExerciseRepository): ViewModel() {
    private val _filter = MutableStateFlow("")
    val filter = _filter.asStateFlow()
    lateinit var alreadySelected: List<Long>

    var selectedItems = mutableMapOf<Long, Boolean>()

    fun updateSelectedItems(id: Long){
        selectedItems.compute(id) { _, value ->
            if (value == null) true else !value
        }
    }

    fun clearSelectedItems() {
        selectedItems.clear()
    }

    var exercises = filter.debounce(750).flatMapLatest { fltr ->
        exerciseRepository.getByFilterPagedWithoutExercises(fltr, alreadySelected).cachedIn(viewModelScope)
    }

    suspend fun getExercisesByIds(ids: List<Long>): List<Exercise> {
        return exerciseRepository.getByIds(ids)
    }

    fun updateFilter(text: String) {
        _filter.update { text }
    }
}