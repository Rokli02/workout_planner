package hu.jszf.marko.workoutplanner.presentation.allActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import hu.jszf.marko.workoutplanner.db.repository.WorkoutActivityRepository
import hu.jszf.marko.workoutplanner.model.WorkoutActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class AllActivityViewModel(private val activityRepository: WorkoutActivityRepository): ViewModel() {
    private val _inputFilter = MutableStateFlow("")
    val inputFilter: StateFlow<String> = _inputFilter

    fun updateInputFilter(filter: String) {
        _inputFilter.update { filter }
    }

    var workoutActivitiesPaged: Flow<PagingData<WorkoutActivity>> = inputFilter.debounce(timeoutMillis = 750).flatMapLatest { filter ->
        getActivitiesByFilter(filter)
    }

    private fun getActivitiesByFilter(filter: String?): Flow<PagingData<WorkoutActivity>> {
        return activityRepository.getByFilterPaged(filter ?: "").cachedIn(viewModelScope)
    }
}