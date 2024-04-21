package hu.jszf.marko.workoutplanner.presentation.createExercise

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.jszf.marko.workoutplanner.model.Exercise
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.component.CustomButton
import hu.jszf.marko.workoutplanner.ui.component.InputField
import hu.jszf.marko.workoutplanner.ui.component.PicPickerField
import hu.jszf.marko.workoutplanner.ui.layout.BasicLayout
import hu.jszf.marko.workoutplanner.ui.snackbar.SnackbarType
import hu.jszf.marko.workoutplanner.ui.theme.ExercisesRes
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import kotlinx.coroutines.launch

@Composable
fun CreateExerciseView(
    exercise: Exercise?,
    showSnackbar: (text: String, type: SnackbarType) -> Unit,
    create: suspend (Exercise) -> Boolean,
    modify: suspend (Exercise) -> Boolean,
) {
    val isNew = exercise == null
    val submitBtnText = if (isNew) "Létrehozás" else "Módosítás"

    var formName by rememberSaveable(inputs = arrayOf(exercise?.name)) { mutableStateOf(exercise?.name ?: "") }
    var formDescription by rememberSaveable(inputs = arrayOf(exercise?.description)) { mutableStateOf(exercise?.description ?: "") }
    var formImg by rememberSaveable(inputs = arrayOf(exercise?.imgId)) { mutableStateOf(exercise?.imgId) }

    BasicLayout (
        bottomBar = {
            var isSubmitBtnEnabled by rememberSaveable {
                mutableStateOf(true)
            }

            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(Dimensions.ScreenPaddigInline, Dimensions.ScreenPaddigBlock),
                contentAlignment = Alignment.Center,
            ) {
                val crScope = rememberCoroutineScope()

                CustomButton(text = submitBtnText, enabled = isSubmitBtnEnabled) {
                    if (formName.isBlank()) {
                        showSnackbar("Kötelező nevet adni", SnackbarType.Fail)

                        return@CustomButton
                    }

                    isSubmitBtnEnabled = false

                    crScope.launch {

                        val formDataExercise = Exercise(
                            id = exercise?.id,
                            name = formName,
                            description = formDescription,
                            imgId = formImg,
                        )

                        if (isNew) {
                            create(formDataExercise).also {
                                if (it) {
                                    showSnackbar("Sikeres gyakorlat felvétel", SnackbarType.Success)

                                    formImg = null
                                    formName = ""
                                    formDescription = ""

                                    return@also
                                }

                                showSnackbar("Sikertelen gyakorlat felvétel", SnackbarType.Fail)
                            }
                        } else {
                            modify(formDataExercise).also {
                                if (it) {
                                    showSnackbar("Sikeres gyakorlat módosítás", SnackbarType.Success)

                                    return@also
                                }

                                showSnackbar("Sikertelen gyakorlat módosítás", SnackbarType.Fail)
                            }
                        }

                        isSubmitBtnEnabled = true
                    }
                }
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimensions.ScreenPaddigInline, Dimensions.ScreenPaddigBlock)
        ) {
            Text(
                text = "Gyakorlat felvétel",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = FontColor,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimensions.HalfElementGap))
            PicPickerField(array = ExercisesRes, valueId = formImg) { formImg = it }

            Spacer(Modifier.height(Dimensions.ElementGap))
            InputField(value = formName, onChange = { formName = it }, label = "Név")

            Spacer(Modifier.height(Dimensions.ElementGap))
            InputField(value = formDescription, onChange = { formDescription = it }, label = "Leírás", lines = 4)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateExerciseViewPreview() {
    CreateExerciseView(
        exercise = null,
        showSnackbar = { _, _ -> },
        create = { true },
        modify = { true },
    )
}