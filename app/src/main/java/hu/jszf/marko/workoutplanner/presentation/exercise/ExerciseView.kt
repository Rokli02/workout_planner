package hu.jszf.marko.workoutplanner.presentation.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
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
import hu.jszf.marko.workoutplanner.model.Exercise
import hu.jszf.marko.workoutplanner.presentation.NavigatorViewModel
import hu.jszf.marko.workoutplanner.presentation.Screen
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.layout.BasicLayout
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import hu.jszf.marko.workoutplanner.ui.theme.RedSecondary
import hu.jszf.marko.workoutplanner.ui.theme.UnknownMuscleRes

@Composable
fun ExerciseView(exercise: Exercise) {
    val isIndependent = true

    BasicLayout (
        extraIcons = {
            if (!isIndependent) {
                return@BasicLayout
            }
            val navVM = viewModel<NavigatorViewModel>(factory = WorkoutApplication.appModule.navigatorViewModelFactory)

            IconButton(
                onClick = {
                    navVM.navController.navigate("${Screen.CreateExerciseScreen.route}${Screen.CreateExerciseScreen.getOptionalArgs(mapOf("exerciseId" to exercise.id))}")
                },
            ) {
                Icon(
                    imageVector = Icons.Outlined.Build,
                    contentDescription = "copy exercise to another",
                    tint = Color.Black,
                    modifier = Modifier.size(Dimensions.IconSize)
                )
            }
        }
    ) {
        val shape = RoundedCornerShape(8.dp)

        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    Dimensions.ScreenPaddigInline,
                    0.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item (
                key = exercise.imgId,
                contentType = "Img"
            ) {
                Image(
                    painter = painterResource(id = exercise.imgId ?: UnknownMuscleRes),
                    contentDescription = "unknown_muscle_res",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(275.dp)
                        .border(Dimensions.BorderThickness, RedSecondary, shape)
                )
            }

            item (
                key = exercise.id,
                contentType = "Text"
            ) {
                Spacer(modifier = Modifier.height(Dimensions.HalfElementGap))

                Text(
                    text = exercise.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    color = FontColor,
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = exercise.description,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = FontColor,
                )
                Spacer(modifier = Modifier.height(Dimensions.HalfElementGap))

                Box(modifier = Modifier.fillMaxWidth().height(Dimensions.BorderThickness).background(Color.Black))
            }

//            if (!isIndependent) {
//                //TODO: Ha össze van kötve egy edzés tervvel: items(count = 0) {}
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExerciseViewPreview() {
    ExerciseSkeletonView()
}