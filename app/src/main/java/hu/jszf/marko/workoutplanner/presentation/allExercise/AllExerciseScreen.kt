package hu.jszf.marko.workoutplanner.presentation.allExercise

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
fun AllExerciseScreen() {
    val exerciseVM = WorkoutApplication.appModule.getAllExerciseViewModel()
    val filter by exerciseVM.filter.collectAsState()
    val exercisesLPI = exerciseVM.exercises.collectAsLazyPagingItems()

    AllExerciseView(
        filter = filter,
        onChange = exerciseVM::updateFilter,
        exercisesLPI = exercisesLPI,
    )
}

fun NavGraphBuilder.allExerciseScreenGraphComposable(navController: NavHostController) {
    composable(route = Screen.AllExerciseScreen.route) {
        AllExerciseScreen()
    }
}