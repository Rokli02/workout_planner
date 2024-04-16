package hu.jszf.marko.workoutplanner.presentation.createActivity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.presentation.Screen
import hu.jszf.marko.workoutplanner.presentation.viewModelFactory

@Composable
fun CreateActivityScreen(workoutActivityId: Long?) {
    val createActivityVM = viewModel<CreateActivityViewModel>(factory = viewModelFactory { CreateActivityViewModel(WorkoutApplication.appModule.workoutActivityRepository) })
    val scope = rememberCoroutineScope()

    LaunchedEffect(scope) {
        createActivityVM.processId(workoutActivityId)
    }

    val activityState = createActivityVM.woActivity.collectAsState()

    CreateActivityView(activityState, createActivityVM.isNew.collectAsState())
}

fun NavGraphBuilder.createActivityScreenGraphcomposable(navController: NavHostController) {
    composable(route = "${Screen.CreateActivityScreen.route}?workoutActivityId={workoutActivityId}", arguments = listOf(
        navArgument(name = "workoutActivityId") {
            type = NavType.LongType
            defaultValue = -1L
            nullable = false
        }
    )) {
        CreateActivityScreen(it.arguments?.getLong("workoutActivityId"))
    }
}