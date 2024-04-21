package hu.jszf.marko.workoutplanner.presentation.createActivity

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.jszf.marko.workoutplanner.model.Exercise
import hu.jszf.marko.workoutplanner.model.WorkoutActivity
import hu.jszf.marko.workoutplanner.presentation.Screen
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.component.ConfirmationDialog
import hu.jszf.marko.workoutplanner.ui.component.CustomButton
import hu.jszf.marko.workoutplanner.ui.component.DatePickerField
import hu.jszf.marko.workoutplanner.ui.component.ExerciseView
import hu.jszf.marko.workoutplanner.ui.component.InputField
import hu.jszf.marko.workoutplanner.ui.component.PicPickerField
import hu.jszf.marko.workoutplanner.ui.layout.BasicLayout
import hu.jszf.marko.workoutplanner.ui.snackbar.SnackbarType
import hu.jszf.marko.workoutplanner.ui.theme.ActivitiesRes
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import hu.jszf.marko.workoutplanner.ui.theme.RedVibrant
import hu.jszf.marko.workoutplanner.ui.theme.UnknownMuscleRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

// TODO: Lehessen törölni a gyakorlatokat
//      removeExercise: List
//      a hozzáadott már benne van a formExercises listában
@SuppressLint("MutableCollectionMutableState") // Sajnálom hogy ehhez kellett folyamondom, már nincs más ötletem miként lehet ezt megcsinálni, de sajna időm sincs kideríteni
@Composable
internal fun CreateActivityView(
    woActivity: WorkoutActivity?,
    isNew: State<Boolean>,
    create: suspend (WorkoutActivity) -> Boolean,
    update: suspend (WorkoutActivity) -> Boolean,
    goBack: (route: String?) -> Unit,
    showSnackbar: (text: String, type: SnackbarType) -> Unit,
    delete: suspend (Long) -> Boolean,
) {
    val submitBtnText = if (isNew.value) "Létrehozás" else "Módosítás"
    val crScope = rememberCoroutineScope()
    var formName by rememberSaveable(inputs = arrayOf(woActivity?.name)) { mutableStateOf(woActivity?.name ?: "") }
    var formDate by rememberSaveable(inputs = arrayOf(woActivity?.date)) { mutableLongStateOf( woActivity?.date?.timeInMillis ?: Calendar.getInstance().timeInMillis) }
    var formImg by rememberSaveable(inputs = arrayOf(woActivity?.imgId)) { mutableStateOf( woActivity?.imgId) }
    var formExercises by rememberSaveable(inputs = arrayOf(woActivity?.exercises)) {
        mutableStateOf(mutableListOf<Exercise>().apply { addAll(woActivity?.exercises ?: listOf()) })
    }
//    var formRemoveExercises = rememberSaveable { mutableListOf<Long>() }

    var isSelectorDialogOpen by remember { mutableStateOf(false) }
    var isDeleteDialogOpen by remember { mutableStateOf(false) }
    val exerciseIds = remember(formExercises, formExercises.size) { formExercises.map { it.id!! }}

    when {
        isSelectorDialogOpen -> {
            ExerciseSelectorDialogView(
                closeDialog = { addedExercises ->
                    isSelectorDialogOpen = false

                    if (addedExercises != null) {
                        formExercises.addAll(addedExercises)
                    }
                },
                alreadySelected = exerciseIds
            )
        }
        isDeleteDialogOpen -> {
            ConfirmationDialog(
                title = "Biztos törölni szeretnéd az edzéstervet?",
                confirmationText = "Biztos",
                onConfirmation = {
                    woActivity?.id?.also {
                        crScope.launch {
                            val isDeleted = delete(it)

                            if (isDeleted) {
                                showSnackbar("Edzésterv törlésre került", SnackbarType.Success)
                                goBack(Screen.HomeScreen.route)
                            } else {
                                showSnackbar("Nem sikerült törölni az edzéstervet", SnackbarType.Default)
                            }
                        }
                    }
                },
                onDecline = {}
            ) {
                isDeleteDialogOpen = false
            }
        }
    }

    BasicLayout (
        extraIcons = {
            IconButton(
                onClick = {
                    isDeleteDialogOpen = true
                },
                colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Black)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Delete activity",
                    modifier = Modifier.size(Dimensions.IconSize)
                )
            }
        }
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimensions.ScreenPaddigInline, Dimensions.ScreenPaddigBlock)
        ) {
            item {
                Text(
                    text = "Edzés Tervezés",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = FontColor,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(Dimensions.HalfElementGap))
                PicPickerField(array = ActivitiesRes, valueId = formImg) { formImg = it }

                Spacer(Modifier.height(Dimensions.ElementGap))
                InputField(label = "Név", value = formName, onChange = { formName = it }, modifier = Modifier.fillMaxWidth())

                Spacer(Modifier.height(Dimensions.ElementGap))
                DatePickerField(label = "Időpont", value = formDate, onChange = { formDate = it })

                Spacer(Modifier.height(Dimensions.HalfElementGap))
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.BorderThickness)
                    .background(Color.Black))
            }

            items(
                count = formExercises.size,
                key = { "${it}_${formExercises[it].id}" }
            ) {
                Spacer(Modifier.height(Dimensions.HalfElementGap))
                ExerciseView(exercise = formExercises[it]) {  }
                Spacer(Modifier.height(Dimensions.HalfElementGap))
            }

            item {
                Spacer(Modifier.height(Dimensions.HalfElementGap))

                Button(
                    onClick = { isSelectorDialogOpen = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp),
                    shape = CircleShape,
                    modifier = Modifier
                        .background(RedVibrant, CircleShape)
                        .border(1.dp, Color.Black, CircleShape)
                        .size(Dimensions.IconSize)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "+",
                        tint = Color.Black,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                Spacer(Modifier.height(Dimensions.HalfElementGap))
            }

            item {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.BorderThickness)
                    .background(Color.Black))
                Spacer(Modifier.height(Dimensions.HalfElementGap))

                val coroutineScope = rememberCoroutineScope()
                var isSubmitBtnEnabled by rememberSaveable {
                    mutableStateOf(true)
                }

                CustomButton(text = submitBtnText, enabled = isSubmitBtnEnabled) {
                    if (formName.isBlank()) {
                        showSnackbar("Kötelező nevet adni", SnackbarType.Fail)

                        return@CustomButton
                    }

                    isSubmitBtnEnabled = false

                    val formDataWOActivity = WorkoutActivity(
                        id = woActivity?.id,
                        name = formName.trim(),
                        date = Calendar.getInstance().apply{ setTimeInMillis(formDate) },
                        imgId = formImg ?: UnknownMuscleRes,
                        exercises = formExercises.toList(),
                    )

                    coroutineScope.launch {
                        if (isNew.value) {
                            create(formDataWOActivity).also {
                                if (it) {
                                    showSnackbar("Hozzáadva", SnackbarType.Success)

                                    formName = ""
                                    formDate = Calendar.getInstance().timeInMillis
                                    formExercises = mutableListOf()
                                } else {
                                    showSnackbar("Nem sikerült létrehozni", SnackbarType.Fail)
                                }
                            }
                        } else {
                            update(formDataWOActivity).also {
                                if (it) {
                                    showSnackbar("Sikeres módosítás", SnackbarType.Success)
                                    goBack(null)
                                } else {
                                    showSnackbar("Sikertelen módosítás", SnackbarType.Fail)
                                }
                            }
                        }

                        isSubmitBtnEnabled = true
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateActivityViewPreview() {
    CreateActivityView(
        woActivity = null,
        isNew = MutableStateFlow(false).collectAsState(),
        create = { true },
        update = { true },
        goBack = {},
        showSnackbar = { _, _ ->  },
        delete = { true }
    )
}