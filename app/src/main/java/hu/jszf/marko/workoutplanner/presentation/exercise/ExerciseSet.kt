package hu.jszf.marko.workoutplanner.presentation.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import hu.jszf.marko.workoutplanner.ui.theme.FontColorDark
import hu.jszf.marko.workoutplanner.ui.theme.RedSecondary
import hu.jszf.marko.workoutplanner.ui.theme.WhiteTranslucent
import kotlin.math.max
import kotlin.math.min

private const val MIN_DRAG_OFFSET = 0f
private const val MAX_DRAG_OFFSET = -500f
private const val ACTION_DRAG_VELOCITY = -750f
private const val INVISIBLE_DRAG_OFFSET = -150f
private const val INVISIBLE_DRAG_OFFSET_MULTIPLIER = -1/300f

@Composable
fun ExerciseSet(
    set: Int,
    reps: Int,
    weight: Int,
    isEditMode: Boolean = false,
    onRemove: () -> Unit,
    onChange: (rep: Int, weight: Int) -> Unit,
) {
    val roundedShape = RoundedCornerShape(Dimensions.Roundness)

    val modifier = if (isEditMode) {
        var offsetX by remember { mutableFloatStateOf(0f) }
        val draggableState = rememberDraggableState {
            offsetX = max(MAX_DRAG_OFFSET, min(MIN_DRAG_OFFSET, offsetX + (it / 3)))
        }

        Modifier
            .offset(x = offsetX.dp)
            .draggable(
                state = draggableState,
                orientation = Orientation.Horizontal,
                onDragStopped = {
                    if (it <= ACTION_DRAG_VELOCITY || offsetX < INVISIBLE_DRAG_OFFSET) {
                        onRemove()

                        return@draggable
                    }

                    offsetX = 0f
                }
            )
            .alpha(1f - min(1f, offsetX * INVISIBLE_DRAG_OFFSET_MULTIPLIER))
    } else Modifier

    Row (
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(roundedShape)
            .background(RedSecondary, roundedShape)
            .padding(12.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val textSize = 20.sp
        var repsTxt by rememberSaveable { mutableStateOf("$reps") }
        var weightTxt by rememberSaveable { mutableStateOf("$weight") }

        Text(text = "$set. Sorozat:", fontSize = textSize, color = FontColor)

        Row (verticalAlignment = Alignment.CenterVertically) {
            LocalInputField(repsTxt, enabled = isEditMode) {
                val isNumber = it.all{ char -> char.isDigit() }
                if (isNumber || it.isEmpty()) {
                    repsTxt = it

                    if (isNumber && it.isNotBlank()) {
                        onChange(repsTxt.toInt(), weightTxt.toInt())
                    }
                }
            }

            Spacer(modifier = Modifier.width(4.dp))
            Text("ismétlés", fontSize = textSize, color = FontColor)
        }

        Row (verticalAlignment = Alignment.CenterVertically) {
            LocalInputField(weightTxt, enabled = isEditMode) {
                val isNumber = it.all{ char -> char.isDigit() }
                if (isNumber || it.isEmpty()) {
                    weightTxt = it

                    if (isNumber && it.isNotBlank()) {
                        onChange(repsTxt.toInt(), weightTxt.toInt())
                    }
                }
            }

            Spacer(modifier = Modifier.width(4.dp))
            Text("kg", fontSize = textSize, color = FontColor)
        }
    }
}

@Preview
@Composable
fun ExerciseSetPreview() {
    ExerciseSet(
        set = 1,
        reps = 8,
        weight = 20,
        isEditMode = false,
        onRemove = {},
    ) { _, _ -> }
}

@Composable
fun LocalInputField(value: String, enabled: Boolean = false, onChange: (String) -> Unit) {
    BasicTextField(
        value = value,
        onValueChange = onChange,
        enabled = enabled,
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(
            fontSize = 18.sp,
            color = FontColor,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        visualTransformation = VisualTransformation.None,
        cursorBrush = SolidColor(FontColorDark),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxHeight()
            .defaultMinSize(minWidth = 32.dp)
            .width(40.dp)
            .clip(RoundedCornerShape(Dimensions.Roundness))
            .background(WhiteTranslucent)
            .padding(4.dp, 4.dp)
    )
}