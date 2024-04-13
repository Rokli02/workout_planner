package hu.jszf.marko.workoutplanner.presentation.activityToday

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.presentation.NavigatorViewModel
import hu.jszf.marko.workoutplanner.presentation.Screen
import hu.jszf.marko.workoutplanner.ui.layout.BasicLayout
import hu.jszf.marko.workoutplanner.ui.component.CustomButton
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import hu.jszf.marko.workoutplanner.ui.theme.RedVibrant

@Composable
fun NoActivityView() {
    val navVm = viewModel<NavigatorViewModel>(factory = WorkoutApplication.appModule.navigatorViewModelFactory)

    BasicLayout {
        Box (
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "A mai napra nincs betervezett edzésed",
                fontSize = 26.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = FontColor,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp, 24.dp, 16.dp, 0.dp),
            )
            
            CustomButton(
                text = "Hozz létre egyet",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(.5f)
                    .padding(0.dp, 0.dp, 0.dp, 60.dp),
                backgroundColor = RedVibrant,
            ) {
                navVm.navController.popBackStack()
                navVm.navController.navigate(Screen.CreateActivityScreen.route)
            }
        }
    }
}

@Preview
@Composable
fun NoActivityScreenPreview() {
    NoActivityView()
}