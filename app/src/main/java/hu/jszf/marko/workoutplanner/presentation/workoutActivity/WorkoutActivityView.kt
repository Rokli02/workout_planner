package hu.jszf.marko.workoutplanner.presentation.workoutActivity

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.model.WorkoutActivity
import hu.jszf.marko.workoutplanner.presentation.NavigatorViewModel
import hu.jszf.marko.workoutplanner.presentation.Screen
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.layout.BasicLayout
import hu.jszf.marko.workoutplanner.ui.component.HorizontalLine
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import hu.jszf.marko.workoutplanner.ui.theme.FontColorMisc
import hu.jszf.marko.workoutplanner.ui.theme.RedSecondary
import hu.jszf.marko.workoutplanner.utils.DateFormatter

@Composable
fun WorkoutActivityView(workoutActivity: WorkoutActivity) {
    val navVm = viewModel<NavigatorViewModel>(factory = WorkoutApplication.appModule.navigatorViewModelFactory)

    BasicLayout (
        extraIcons = {
            IconButton(
                onClick = {
                    navVm.navController.navigate("${Screen.CreateActivityScreen.route}${Screen.CreateActivityScreen.getOptionalArgs(mapOf("workoutActivityId" to workoutActivity.id))}")
                },
            ) {
                Icon(
                    imageVector = Icons.Outlined.Build,
                    contentDescription = "copy activity to another",
                    tint = Color.Black,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    ) {
        LazyColumn (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    Dimensions.ScreenPaddigInline,
                    Dimensions.ScreenPaddigBlock,
                )
        ) {
            /* Header */
            item {
                Image(
                    painter = painterResource(id = workoutActivity.imgId),
                    contentDescription = "unknown_muscle_res",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(275.dp)
                        .border(Dimensions.BorderThickness, RedSecondary, RoundedCornerShape(8.dp))
                )
                Text(
                    text = workoutActivity.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    color = FontColor,
                    modifier = Modifier
                        .padding(0.dp, 8.dp, 0.dp, 2.dp)
                )

                Text(
                    text = DateFormatter.format(workoutActivity.date),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = FontColorMisc,
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 12.dp)
                )

                Row (
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "${workoutActivity.allExercises} gyakorlat",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = FontColor,
                    )

                    Text(
                        text = "${workoutActivity.allSets} ismétlés",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = FontColor,
                    )
                }
            }

            item {
                HorizontalLine(modifier = Modifier.padding(0.dp, 12.dp))
            }

            items(count = 10) {
                Text("$it list item")
            }

            item {
                Text(text = "id: ${workoutActivity.id}", fontSize = 10.sp, fontWeight = FontWeight.Light, color = FontColorMisc)
                HorizontalLine(modifier = Modifier.padding(0.dp, 11 .dp), color = Color.Transparent)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrentActivityViewPreview() {
    WorkoutActivityView(WorkoutActivity(
        id = 0,
        name = "Hatalmas Hasazás",
    ))
}