package hu.jszf.marko.workoutplanner.presentation.allActivity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.paging.compose.collectAsLazyPagingItems
import hu.jszf.marko.workoutplanner.WorkoutApplication

@Composable
fun AllActivityScreen() {
    val allActivityVM = WorkoutApplication.appModule.getAllActivityViewModel()
    val workoutActivitiesLPI = allActivityVM.workoutActivitiesPaged.collectAsLazyPagingItems()
    val filter by allActivityVM.inputFilter.collectAsState()

    AllActivityView(
        filter = filter,
        onChange = allActivityVM::updateInputFilter,
        workoutActivitiesLPI = workoutActivitiesLPI,
    )
}