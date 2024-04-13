package hu.jszf.marko.workoutplanner.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.model.WorkoutActivity
import hu.jszf.marko.workoutplanner.presentation.NavigatorViewModel
import hu.jszf.marko.workoutplanner.presentation.Screen
import hu.jszf.marko.workoutplanner.presentation.viewModelFactory
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import hu.jszf.marko.workoutplanner.ui.theme.FontColorMisc
import hu.jszf.marko.workoutplanner.ui.theme.RedSecondary
import hu.jszf.marko.workoutplanner.utils.DateFormatter

@Composable
fun WorkoutActivityView(workoutActivity: WorkoutActivity, modifier: Modifier = Modifier) {
    val roundedShape = RoundedCornerShape(8.dp)
    val navVM = viewModel<NavigatorViewModel>(factory = WorkoutApplication.appModule.navigatorViewModelFactory)

    Surface(
        onClick = { navVM.navController.navigate("${Screen.WorkoutActivityScreen.route}/${workoutActivity.id}") },
        shape = roundedShape,
        color = RedSecondary,
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(roundedShape)
    ) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
    ) {
            Image(
                painter = painterResource(id = workoutActivity.imgId),
                contentDescription = "workout_img",
                modifier = Modifier
                    .size(76.dp)
                    .offset(2.dp, 0.dp)
                    .clip(roundedShape)
            )

            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, 4.dp, 6.dp, 4.dp)
            ) {
                Text(
                    text = workoutActivity.name,
                    fontSize = 18.sp,
                    color = FontColor,
                    modifier = Modifier.align(Alignment.TopStart)
                )
                Text(
                    text = DateFormatter.format(workoutActivity.date),
                    fontSize = 12.sp,
                    color = FontColorMisc,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
                Text(
                    text = "${workoutActivity.allExercises} gyakorlat",
                    fontSize = 12.sp,
                    color = FontColorMisc,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}

@Preview
@Composable
fun WorkoutActivityViewPreview() {
    WorkoutActivityView(WorkoutActivity(0, "Hassan Bass"))
}