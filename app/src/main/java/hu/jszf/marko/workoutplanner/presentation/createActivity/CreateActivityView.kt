package hu.jszf.marko.workoutplanner.presentation.createActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.model.WorkoutActivity
import hu.jszf.marko.workoutplanner.presentation.NavigatorViewModel
import hu.jszf.marko.workoutplanner.presentation.viewModelFactory
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.blockBorder
import hu.jszf.marko.workoutplanner.ui.component.CustomButton
import hu.jszf.marko.workoutplanner.ui.component.DatePickerField
import hu.jszf.marko.workoutplanner.ui.component.InputField
import hu.jszf.marko.workoutplanner.ui.component.PicPickerField
import hu.jszf.marko.workoutplanner.ui.layout.BasicLayout
import hu.jszf.marko.workoutplanner.ui.snackbar.SnackbarType
import hu.jszf.marko.workoutplanner.ui.theme.ActivitiesRes
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import hu.jszf.marko.workoutplanner.ui.theme.FontColorDark
import hu.jszf.marko.workoutplanner.ui.theme.RedVibrant
import hu.jszf.marko.workoutplanner.ui.theme.UnknownMuscleRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
internal fun CreateActivityView(woActivity: State<WorkoutActivity?>, isNew: State<Boolean>) {
    val createActivityVM = viewModel<CreateActivityViewModel>(factory = viewModelFactory { CreateActivityViewModel(
        WorkoutApplication.appModule.workoutActivityRepository) })
    val snackbarViewModel = WorkoutApplication.appModule.getSnackbarViewModel()
    val navVm = viewModel<NavigatorViewModel>(factory = WorkoutApplication.appModule.navigatorViewModelFactory)

    val submitBtnText = if (isNew.value) "Létrehozás" else "Módosítás"

    var formName by rememberSaveable(inputs = arrayOf(woActivity.value?.name)) { mutableStateOf(woActivity.value?.name ?: "") }
    var formDate by rememberSaveable(inputs = arrayOf(woActivity.value?.date)) { mutableLongStateOf( woActivity.value?.date?.timeInMillis ?: Calendar.getInstance().timeInMillis) }
    var formImg by rememberSaveable(inputs = arrayOf(woActivity.value?.imgId)) { mutableStateOf( woActivity.value?.imgId) }

    BasicLayout {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimensions.ScreenPaddigInline, Dimensions.ScreenPaddigBlock)
        ) {
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

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, Dimensions.ElementGap)
                    .blockBorder(Dimensions.BorderThickness, FontColorDark)
            ) {
                Spacer(Modifier.height(Dimensions.HalfElementGap))

                Button(
                    onClick = { /*TODO: add exercise picker dialog*/ },
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

            val coroutineScope = rememberCoroutineScope()
            var isSubmitBtnEnabled by rememberSaveable {
                mutableStateOf(true)
            }

            CustomButton(text = submitBtnText, enabled = isSubmitBtnEnabled) {
                isSubmitBtnEnabled = false

                val formDataWOActivity = WorkoutActivity(
                    id = woActivity.value?.id,
                    name = formName,
                    date = Calendar.getInstance().apply{ setTimeInMillis(formDate) },
                    imgId = formImg ?: UnknownMuscleRes,
                )

                coroutineScope.launch {
                    if (isNew.value) {
                        createActivityVM.create(formDataWOActivity).also {
                            if (it) {
                                snackbarViewModel.show("Hozzáadva", SnackbarType.Success)

                                formName = ""
                                formDate = Calendar.getInstance().timeInMillis
                            } else {
                                snackbarViewModel.show("Nem sikerült létrehozni", SnackbarType.Fail)
                            }
                        }
                    } else {
                        createActivityVM.update(formDataWOActivity).also {
                            if (it) {
                                snackbarViewModel.show("Sikeres módosítás", SnackbarType.Success)
                                navVm.navController.popBackStack()
                            } else {
                                snackbarViewModel.show("Sikertelen módosítás", SnackbarType.Fail)
                            }
                        }
                    }

                    isSubmitBtnEnabled = true
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateActivityViewPreview() {
    CreateActivityView(MutableStateFlow(null).collectAsState(), MutableStateFlow(false).collectAsState())
}