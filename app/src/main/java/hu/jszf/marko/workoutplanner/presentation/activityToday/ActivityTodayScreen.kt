package hu.jszf.marko.workoutplanner.presentation.activityToday

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.presentation.workoutActivity.WorkoutActivitySkeletonView
import hu.jszf.marko.workoutplanner.presentation.workoutActivity.WorkoutActivityView
import hu.jszf.marko.workoutplanner.presentation.workoutActivity.WorkoutActivityViewModel
import kotlinx.coroutines.flow.update

@Composable
fun ActivityTodayScreen() {
    val coroutineScope = rememberCoroutineScope()
    val workoutActivityVM = viewModel<WorkoutActivityViewModel>(factory = WorkoutApplication.appModule.workoutActivityViewModelFactory)

    var hasActivityToday by rememberSaveable {
        mutableStateOf<Boolean?>(null)
    }

    LaunchedEffect(coroutineScope) {
        workoutActivityVM.getTodaysActivity().also { result ->
            if (result == null) {
                hasActivityToday = false
            } else {
                hasActivityToday = true
                workoutActivityVM.workoutActivity.update { result }
            }
        }

    }

    if (hasActivityToday == null) {
        return WorkoutActivitySkeletonView()
    }

    if (hasActivityToday == false) {
        return NoActivityView()
    }

    val activityToday by workoutActivityVM.workoutActivity.collectAsState()

    WorkoutActivityView(activityToday!!)
}

@Preview
@Composable
fun ActivityTodayPreview() {
    ActivityTodayScreen()
}