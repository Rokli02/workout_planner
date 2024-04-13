package hu.jszf.marko.workoutplanner.presentation.workoutActivity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.model.WorkoutActivity
import kotlinx.coroutines.launch

@Composable
fun WorkoutActivityScreen(workoutActivityId: Long) {
    val workoutActivityVM = viewModel<WorkoutActivityViewModel>(factory = WorkoutApplication.appModule.workoutActivityViewModelFactory)
    val crScope = rememberCoroutineScope()

    LaunchedEffect(crScope) {
        workoutActivityVM.getById(workoutActivityId)
    }

    val workoutActivity: WorkoutActivity? by workoutActivityVM.workoutActivity.collectAsState(initial = null)//WorkoutActivity(workoutActivityId, "WO Activity")
    if (workoutActivity == null) {
        WorkoutActivitySkeletonView()

        return
    }

    WorkoutActivityView(workoutActivity!!)
}
