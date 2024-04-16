package hu.jszf.marko.workoutplanner.presentation.exercise

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
fun ExerciseScreen(exerciseId: Long, activityId: Long?) {
    val exerciseVM = WorkoutApplication.appModule.getExerciseViewModel()
    val crScope = rememberCoroutineScope()

    LaunchedEffect(crScope) {
        println("exerciseId: $exerciseId and activityId: $activityId")
        exerciseVM.getExercise(exerciseId, activityId)
    }

    val exercise by exerciseVM.exercise.collectAsState()

    if (exercise == null) {
        return ExerciseSkeletonView()
    }

    return ExerciseView(exercise!!)
}

fun NavGraphBuilder.exerciseScreenGraphComposable(navController: NavHostController) {
    composable(route = "${Screen.ExerciseScreen.route}/{exerciseId}?activityId={activityId}", arguments = listOf(
        navArgument("exerciseId") {
            type = NavType.LongType
            defaultValue = -1
            nullable = false
        },
        navArgument("activityId") {
            type = NavType.LongType
            defaultValue = -1
            nullable = false
        }
    )) { entry ->
        val exerciseId = entry.arguments?.getLong("exerciseId")?.let { if (it == -1L) null else it }
        val activityId = entry.arguments?.getLong("activityId")?.let { if (it == -1L) null else it }

        if (exerciseId == null)
            navController.navigate(Screen.HomeScreen.route)
        else
            ExerciseScreen(
                exerciseId = exerciseId,
                activityId = activityId,
            )

    }
}