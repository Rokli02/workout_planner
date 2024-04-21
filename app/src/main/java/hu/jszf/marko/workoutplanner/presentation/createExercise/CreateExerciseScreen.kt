package hu.jszf.marko.workoutplanner.presentation.createExercise

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.presentation.Screen

@Composable
fun CreateExerciseScreen(exerciseId: Long?) {
    val crScope = rememberCoroutineScope()
    val snackbarVM = WorkoutApplication.appModule.getSnackbarViewModel()
    val exerciseVM = WorkoutApplication.appModule.getCreateExerciseViewModel()

    LaunchedEffect(crScope) {
        exerciseVM.getExerciseById(exerciseId)
    }

    val exercise by exerciseVM.exercise.collectAsState()

    CreateExerciseView(
        exercise = exercise,
        showSnackbar = snackbarVM::show,
        create = exerciseVM::create,
        modify = exerciseVM::modify,
    )
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