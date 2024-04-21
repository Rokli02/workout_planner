package hu.jszf.marko.workoutplanner.presentation.createActivity

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
import hu.jszf.marko.workoutplanner.presentation.viewModelFactory

@Composable
fun CreateActivityScreen(workoutActivityId: Long?) {
    val createActivityVM = viewModel<CreateActivityViewModel>(factory = viewModelFactory { CreateActivityViewModel(WorkoutApplication.appModule.workoutActivityRepository) })
    val navVm = viewModel<NavigatorViewModel>(factory = WorkoutApplication.appModule.navigatorViewModelFactory)
    val snackbarViewModel = WorkoutApplication.appModule.getSnackbarViewModel()
    val scope = rememberCoroutineScope()

    LaunchedEffect(scope) {
        createActivityVM.processId(workoutActivityId)
    }

    val activityState by createActivityVM.woActivity.collectAsState()

    CreateActivityView(
        woActivity = activityState,
        isNew = createActivityVM.isNew.collectAsState(),
        create = createActivityVM::create,
        update = createActivityVM::update,
        goBack = { route ->
            if (route == null)
                navVm.navController.popBackStack()
            else
                navVm.navController.popBackStack(route, inclusive = false)
         },
        showSnackbar = { text, type -> snackbarViewModel.show(text, type) },
        delete = createActivityVM::delete,
    )
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