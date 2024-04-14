package hu.jszf.marko.workoutplanner.presentation.createActivity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.presentation.viewModelFactory

@Composable
fun CreateActivityScreen(workoutActivityId: Long?) {
    val createActivityVM = viewModel<CreateActivityViewModel>(factory = viewModelFactory { CreateActivityViewModel(WorkoutApplication.appModule.workoutActivityRepository) })
    val scope = rememberCoroutineScope()

    LaunchedEffect(scope) {
        createActivityVM.processId(workoutActivityId)
    }

    CreateActivityView(createActivityVM.woActivity.collectAsState(), createActivityVM.isNew.collectAsState())
}