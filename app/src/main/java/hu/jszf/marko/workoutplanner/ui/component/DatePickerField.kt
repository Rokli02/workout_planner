package hu.jszf.marko.workoutplanner.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import hu.jszf.marko.workoutplanner.ui.theme.FontColorMisc
import hu.jszf.marko.workoutplanner.ui.theme.RedPrimary
import hu.jszf.marko.workoutplanner.ui.theme.RedSecondary
import hu.jszf.marko.workoutplanner.ui.theme.RedVibrant
import hu.jszf.marko.workoutplanner.utils.DateFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(label: String, value: Long, onChange: (value: Long) -> Unit) {
    Column {
        var isDatePickerOpen by remember {
            mutableStateOf(false)
        }
        val txtFieldShape = RoundedCornerShape(8.dp)

        Text(
            text = label,
            color = FontColor,
            fontSize = Dimensions.InputFieldLabelSize,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(4.dp))

        CustomButton(
            text = DateFormatter.format(Calendar.getInstance().apply{ setTimeInMillis(value) }.time)
        ) { isDatePickerOpen = true }

        if (isDatePickerOpen) {
            val dpState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker, initialSelectedDateMillis = value)

            DatePickerDialog(
                onDismissRequest = {
                    isDatePickerOpen = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            isDatePickerOpen = false

                            onChange(dpState.selectedDateMillis ?: Calendar.getInstance().time.time)
                        },
                        modifier = Modifier
                            .clip(txtFieldShape)
                            .background(Color.Green)
                    ) {
                        Text("Elfogad", color = Color.Black)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            isDatePickerOpen = false
                        },
                        modifier = Modifier
                            .clip(txtFieldShape)
                            .background(Color.Red)
                    ) {
                        Text("Elutasít", color = Color.Black)
                    }
                },
                colors = DatePickerDefaults.colors(
                    containerColor = RedPrimary,
                )
            ) {
                DatePicker(
                    state = dpState,
                    colors = DatePickerDefaults.colors(
                        containerColor = RedPrimary,
                        currentYearContentColor = FontColor,
                        titleContentColor = FontColor,
                        headlineContentColor = FontColor,
                        subheadContentColor = FontColor,
                        dayContentColor = FontColor,
                        yearContentColor = FontColor,
                        todayDateBorderColor = RedSecondary,
                        todayContentColor = RedVibrant,
                        dayInSelectionRangeContainerColor = RedSecondary,
                        selectedDayContainerColor = RedSecondary,
                        selectedDayContentColor = FontColor,
                        selectedYearContainerColor = RedVibrant,
                        selectedYearContentColor = FontColor,
                        weekdayContentColor = RedVibrant,
                        dayInSelectionRangeContentColor = RedVibrant,
                        disabledSelectedDayContainerColor = FontColorMisc,
                        disabledDayContentColor = FontColorMisc,
                        disabledSelectedDayContentColor = FontColorMisc,
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
fun DatePickerFieldPreview() {

    val tomorow = Calendar.getInstance().apply {
        this.add(Calendar.DATE, 2)
    }
    DatePickerField(label = "Dátum mező label", value = tomorow.time.time, onChange = {})
}