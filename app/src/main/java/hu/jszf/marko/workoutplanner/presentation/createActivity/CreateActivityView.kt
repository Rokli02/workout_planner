package hu.jszf.marko.workoutplanner.presentation.createActivity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
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
import hu.jszf.marko.workoutplanner.ui.layout.BasicLayout
import hu.jszf.marko.workoutplanner.ui.snackbar.SnackbarType
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import hu.jszf.marko.workoutplanner.ui.theme.FontColorDark
import hu.jszf.marko.workoutplanner.ui.theme.RedPrimary
import hu.jszf.marko.workoutplanner.ui.theme.RedSecondary
import hu.jszf.marko.workoutplanner.ui.theme.RedVibrant
import hu.jszf.marko.workoutplanner.ui.theme.UnknownMuscleRes
import hu.jszf.marko.workoutplanner.ui.theme.WhiteTranslucent
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

    BasicLayout {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(RedPrimary)
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

            val btnShape = RoundedCornerShape(8.dp)
            Button(
                onClick = { /*TODO: Ha nincs kép kiválasztva, default kép, különben a kiválasztott*/ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp),
                shape = btnShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(275.dp)
                    .clip(btnShape)
                    .background(WhiteTranslucent)
                    .border(Dimensions.BorderThickness, RedSecondary, btnShape)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = UnknownMuscleRes),
                        contentDescription = "unknown_muscle_res",
                        contentScale = ContentScale.FillBounds,
                        alpha = 0.28f,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    Text(
                        text = "Kép hozzáadás",
                        fontSize = 16.sp,
                        style = TextStyle.Default.copy(
                            color = FontColorDark,
                            letterSpacing = TextUnit(.1f, TextUnitType.Sp),
                            drawStyle = Stroke(
                                width = 4f,
                                miter = 4f,
                                join = StrokeJoin.Round
                            )
                        ),
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    Text(
                        text = "Kép hozzáadás",
                        fontSize = 16.sp,
                        color = FontColor,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

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
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp),
                    shape = CircleShape,
                    modifier = Modifier
                        .background(RedVibrant, CircleShape)
                        .border(1.dp, Color.Black, CircleShape)
                        .size(36.dp)
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

                coroutineScope.launch {
                    val _woActivity = WorkoutActivity(
                        id = woActivity.value?.id,
                        name = formName,
                        date = Calendar.getInstance().apply{ setTimeInMillis(formDate) },
                    )

                    if (isNew.value) {
                        createActivityVM.create(_woActivity).also {
                            if (it) {
                                snackbarViewModel.show("Hozzáadva", SnackbarType.Success)

                                formName = ""
                                formDate = Calendar.getInstance().timeInMillis
                            } else {
                                snackbarViewModel.show("Nem sikerült létrehozni", SnackbarType.Fail)
                            }
                        }
                    } else {
                        createActivityVM.update(_woActivity).also {
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