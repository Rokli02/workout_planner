package hu.jszf.marko.workoutplanner.presentation.createExercise

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.presentation.NavigatorViewModel
import hu.jszf.marko.workoutplanner.presentation.Screen

@Composable
fun CreateExerciseScreen(exerciseId: Long?) {
    val crScope = rememberCoroutineScope()
    val snackbarVM = WorkoutApplication.appModule.getSnackbarViewModel()
    val exerciseVM = WorkoutApplication.appModule.getCreateExerciseViewModel()
    val navVM = viewModel<NavigatorViewModel>(factory = WorkoutApplication.appModule.navigatorViewModelFactory)

    LaunchedEffect(crScope) {
        exerciseVM.getExerciseById(exerciseId)
    }

    val exercise by exerciseVM.exercise.collectAsState()

    CreateExerciseView(
        exercise = exercise,
        showSnackbar = snackbarVM::show,
        create = exerciseVM::create,
        modify = exerciseVM::modify,
        delete = exerciseVM::delete,
        goBack = {
            if (it == null) {
                navVM.navController.popBackStack()
            } else {
                navVM.navController.popBackStack(it, false)
            }
        }
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