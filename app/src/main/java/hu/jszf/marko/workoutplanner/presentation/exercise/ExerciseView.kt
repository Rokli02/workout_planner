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
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.jszf.marko.workoutplanner.model.ActivityExercise
import hu.jszf.marko.workoutplanner.model.Exercise
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.component.ConfirmationDialog
import hu.jszf.marko.workoutplanner.ui.layout.BasicLayout
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import hu.jszf.marko.workoutplanner.ui.theme.RedSecondary
import hu.jszf.marko.workoutplanner.ui.theme.RedVibrant
import hu.jszf.marko.workoutplanner.ui.theme.UnknownMuscleRes

@Composable
fun ExerciseView(
    exercise: Exercise,
    modifyExercise: () -> Unit,
    updateActivityExercise: (exercise: Exercise) -> Unit,
    editModeOnInit: Boolean = false,
) {
    var editMode by rememberSaveable { mutableStateOf(editModeOnInit) }
    var isModified by rememberSaveable { mutableStateOf(false) }
    var isDialogOpen by rememberSaveable { mutableStateOf(false) }
    var formExercise by rememberSaveable(inputs = arrayOf(exercise)) {
        mutableStateOf(exercise.copy(activityExercise = exercise.activityExercise?.copy(
            reps = exercise.activityExercise.reps.map { it }.toMutableList(),
            weights = exercise.activityExercise.weights.map { it }.toMutableList(),
        )))
    }

    if (isDialogOpen) {
        ConfirmationDialog(
            title = "Biztos, hogy végrehajtod a módosítást?",
            confirmationText = "Igen",
            onConfirmation = {
                isModified = false
                editMode = false
                updateActivityExercise(formExercise)
            },
            onDecline = {
                isModified = false
                editMode = false
                formExercise = exercise.copy(activityExercise = exercise.activityExercise?.copy())
            }
        ) {
            isDialogOpen = false
        }
    }

    BasicLayout (
        extraIcons = {
            if (formExercise.activityExercise != null) {
                val containerColor = if (editMode) RedSecondary else Color.Transparent

                IconButton(
                    onClick = {
                        if (editMode && isModified) {
                            isDialogOpen = true

                            return@IconButton
                        }

                        editMode = !editMode
                    },
                    colors = IconButtonDefaults.iconButtonColors(containerColor = containerColor, contentColor = Color.Black)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Build,
                        contentDescription = "Switch to Edit Mode",
                        modifier = Modifier.size(Dimensions.IconSize),
                    )
                }

                return@BasicLayout
            }

            IconButton(
                onClick = {
                    modifyExercise()
                },
                colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Transparent, contentColor = Color.Black)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Build,
                    contentDescription = "Modify exercise",
                    modifier = Modifier.size(Dimensions.IconSize),
                )
            }
        }
    ) {
        val shape = RoundedCornerShape(Dimensions.Roundness)

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
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.BorderThickness)
                    .background(Color.Black))
            }

            if (formExercise.activityExercise != null) {
                if (formExercise.activityExercise!!.sets != 0) {
                    items(
                        count = formExercise.activityExercise!!.sets,
                        key = { "${it}_${formExercise.activityExercise!!.sets}_${formExercise.activityExercise!!.reps[it]}_${formExercise.activityExercise!!.weights[it]}" }
                    ) { index ->
                        Spacer(modifier = Modifier.height(Dimensions.HalfElementGap))
                        ExerciseSet(
                            set = (index + 1),
                            reps = formExercise.activityExercise!!.reps[index],
                            weight = formExercise.activityExercise!!.weights[index],
                            isEditMode = editMode,
                            onRemove = {
                                formExercise.activityExercise!!.reps.removeAt(index)
                                formExercise.activityExercise!!.weights.removeAt(index)

                                val activityExercise = ActivityExercise(
                                    activityId = formExercise.activityExercise!!.activityId,
                                    exerciseId = formExercise.id!!,
                                    sets = formExercise.activityExercise!!.sets - 1,
                                    reps = formExercise.activityExercise!!.reps,
                                    weights = formExercise.activityExercise!!.weights,
                                )

                                isModified = true
                                formExercise = formExercise.copy(
                                    activityExercise = activityExercise
                                )
                            },
                        ) { rep, weight ->
                            formExercise.activityExercise!!.reps[index] = rep
                            formExercise.activityExercise!!.weights[index] = weight
                            isModified = true
                        }
                        Spacer(modifier = Modifier.height(Dimensions.HalfElementGap))
                    }
                }

                if (editMode) {
                    item {
                        IconButton(
                            onClick = {
                                val reps = formExercise.activityExercise?.reps?.apply{
                                    if (size > 0) {
                                        add(last())
                                    } else {
                                        add(0)
                                    }
                                }

                                val weights = formExercise.activityExercise?.weights?.apply{
                                    if (size > 0) {
                                        add(last())
                                    } else {
                                        add(0)
                                    }
                                }

                                val newActivityExercise = ActivityExercise(
                                    activityId = formExercise.activityExercise!!.activityId,
                                    exerciseId = formExercise.id!!,
                                    sets = formExercise.activityExercise!!.sets + 1,
                                    reps = reps!!,
                                    weights = weights!!,
                                )

                                formExercise = formExercise.copy(
                                    activityExercise = newActivityExercise
                                )
                                isModified = true
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = RedVibrant,
                                contentColor = FontColor,
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Add,
                                contentDescription = "copy exercise to another",
                                tint = Color.Black,
                                modifier = Modifier.size(Dimensions.IconSize)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ExerciseViewPreview() {
    ExerciseView(
        exercise = Exercise(
            id = -1,
            name = "Komoly gyakorlat",
            description = "Rövid leírás",
            activityExercise = ActivityExercise(
                activityId = -1,
                exerciseId = -1,
                sets = 3,
                reps = mutableListOf(5, 6, 8),
                weights = mutableListOf(40, 50, 55)
            )
        ),
        modifyExercise = {},
        updateActivityExercise= {},
        editModeOnInit = true,
    )
}