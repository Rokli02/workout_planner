package hu.jszf.marko.workoutplanner.presentation.allActivity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.presentation.Screen

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

fun NavGraphBuilder.allActivityScreenGraphComposable(navController: NavHostController) {
    composable(route = Screen.AllActivityScreen.route) {
        AllActivityScreen()
    }
}