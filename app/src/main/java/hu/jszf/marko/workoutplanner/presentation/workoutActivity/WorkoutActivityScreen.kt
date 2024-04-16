package hu.jszf.marko.workoutplanner.presentation.workoutActivity

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
import hu.jszf.marko.workoutplanner.model.WorkoutActivity
import hu.jszf.marko.workoutplanner.presentation.Screen

@Composable
fun WorkoutActivityScreen(workoutActivityId: Long) {
    val workoutActivityVM = viewModel<WorkoutActivityViewModel>(factory = WorkoutApplication.appModule.workoutActivityViewModelFactory)
    val crScope = rememberCoroutineScope()

    LaunchedEffect(crScope) {
        workoutActivityVM.getById(workoutActivityId)
    }

    val workoutActivity: WorkoutActivity? by workoutActivityVM.workoutActivity.collectAsState(initial = null)
    if (workoutActivity == null) {
        WorkoutActivitySkeletonView()

        return
    }

    WorkoutActivityView(workoutActivity!!)
}

fun NavGraphBuilder.workoutActivityScreenGraphComposable(navController: NavHostController) {
    composable(route = "${Screen.WorkoutActivityScreen.route}/{workoutActivityId}", arguments = listOf(
        navArgument(name = "workoutActivityId") {
            type = NavType.LongType
            defaultValue = -1
            nullable = false
        }
    )) {
        val workoutActivityId = it.arguments?.getLong("workoutActivityId")?.let { if (it == -1L) null else it }

        if (workoutActivityId == null)
            navController.navigate(Screen.HomeScreen.route)
        else
            WorkoutActivityScreen(workoutActivityId = workoutActivityId)
    }
}