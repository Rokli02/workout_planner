package hu.jszf.marko.workoutplanner.presentation.activityToday

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import hu.jszf.marko.workoutplanner.model.WorkoutActivity
import hu.jszf.marko.workoutplanner.presentation.workoutActivity.WorkoutActivityView
import hu.jszf.marko.workoutplanner.ui.layout.LoadingView

@Composable
fun ActivityTodayScreen() {
    //TODO: Query for today's workoutActivity
    val hasActivityToday by rememberSaveable {
        mutableStateOf<Boolean?>(true)
    }

    if (hasActivityToday == null) {
        return LoadingView()
    }

    if (hasActivityToday == false) {
        return NoActivityView()
    }

    val workoutActivity = WorkoutActivity(
        id = 0,
        name = "Phantasmal Hasan",
    ).apply {
        allExercises = 40
        allSets = 16
    }

    WorkoutActivityView(workoutActivity)
}

@Preview
@Composable
fun ActivityTodayPreview() {
    ActivityTodayScreen()
}