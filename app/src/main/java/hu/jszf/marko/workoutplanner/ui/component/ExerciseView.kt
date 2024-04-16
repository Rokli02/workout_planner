package hu.jszf.marko.workoutplanner.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.model.Exercise
import hu.jszf.marko.workoutplanner.presentation.NavigatorViewModel
import hu.jszf.marko.workoutplanner.presentation.Screen
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import hu.jszf.marko.workoutplanner.ui.theme.FontColorMisc
import hu.jszf.marko.workoutplanner.ui.theme.RedSecondary
import hu.jszf.marko.workoutplanner.ui.theme.UnknownMuscleRes

@Composable
fun ExerciseView(exercise: Exercise, modifier: Modifier = Modifier) {
    val roundedShape = RoundedCornerShape(8.dp)
    val navVM = viewModel<NavigatorViewModel>(factory = WorkoutApplication.appModule.navigatorViewModelFactory)

    Surface(
        onClick = { navVM.navController.navigate("${Screen.ExerciseScreen.route}/${exercise.id}") },
        shape = roundedShape,
        color = RedSecondary,
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(roundedShape)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = exercise.imgId ?: UnknownMuscleRes),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(2.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp, 3.dp)
            ) {
                Text(
                    text = exercise.name,
                    maxLines = 1,
                    color = FontColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = exercise.description,
                    color = FontColorMisc,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 15.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun ExerciseViewPreview() {
    val exercise = Exercise(
        id = null,
        name = "Gyakorlat neve",
        description = "Egy viszonylag rövid leírűs, amit tisztázza, hogy miként kell a gyakorlatot rövid leírűs, amit tisztázza, hogy miként kell a gyakorlatot"
    )

    ExerciseView(exercise)
}