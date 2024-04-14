package hu.jszf.marko.workoutplanner.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.presentation.NavigatorViewModel
import hu.jszf.marko.workoutplanner.presentation.Screen
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.component.CustomButton
import hu.jszf.marko.workoutplanner.ui.blockBorder

@Composable
internal fun SidebarView() {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                Dimensions.ScreenPaddigInline,
                Dimensions.ScreenPaddigBlock,
            )
    ) {
            val navVm = viewModel<NavigatorViewModel>(factory = WorkoutApplication.appModule.navigatorViewModelFactory)

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, Dimensions.QuarterElementGap, 0.dp, Dimensions.HalfElementGap)
                    .blockBorder(Dimensions.BorderThickness, Color.Black)
            ) {
                CustomButton(text = "Mai Gyakorlataim", modifier = Modifier.padding(0.dp, Dimensions.HalfElementGap, 0.dp, Dimensions.QuarterElementGap)) { navVm.navController.navigate(Screen.ActivityTodayScreen.route) }
                CustomButton(text = "Edzés tervezés", modifier = Modifier.padding(0.dp, Dimensions.QuarterElementGap, 0.dp, Dimensions.HalfElementGap)) { navVm.navController.navigate(Screen.CreateActivityScreen.route) }
            }

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, Dimensions.QuarterElementGap, 0.dp, Dimensions.QuarterElementGap)
                    .blockBorder(Dimensions.BorderThickness, Color.Black)
            ) {
                CustomButton(text = "Új gyakorlat felvétel", enabled = false, modifier = Modifier.padding(0.dp, Dimensions.HalfElementGap, 0.dp, Dimensions.QuarterElementGap)) {}
                CustomButton(text = "Korábbi edzéseim", enabled = false, modifier = Modifier.padding(0.dp, Dimensions.QuarterElementGap, 0.dp, Dimensions.QuarterElementGap)) {}
                CustomButton(text = "Összes gyakorlat", enabled = false, modifier = Modifier.padding(0.dp, Dimensions.QuarterElementGap, 0.dp, Dimensions.HalfElementGap)) {}
            }
        }
}

@Preview(showBackground = true)
@Composable
fun SidebarViewPreview() {
    SidebarView()
}