package hu.jszf.marko.workoutplanner.presentation.createExercise

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hu.jszf.marko.workoutplanner.presentation.Screen

@Composable
fun CreateExerciseScreen(exerciseId: Long?) {
    CreateExerciseView(null)
}

fun NavGraphBuilder.createExerciseScreenGraphcomposable(navController: NavHostController) {
    composable(route = "${Screen.CreateExerciseScreen.route}?exerciseId={exerciseId}", arguments = listOf(
        navArgument(name = "exerciseId") {
            type = NavType.LongType
            defaultValue = -1L
        }
    )) {
        CreateExerciseScreen(it.arguments?.getLong("exerciseId"))
    }
}