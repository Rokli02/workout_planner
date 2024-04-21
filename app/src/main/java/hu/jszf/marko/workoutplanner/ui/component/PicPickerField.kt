package hu.jszf.marko.workoutplanner.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import hu.jszf.marko.workoutplanner.R
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import hu.jszf.marko.workoutplanner.ui.theme.FontColorDark
import hu.jszf.marko.workoutplanner.ui.theme.RedPrimary
import hu.jszf.marko.workoutplanner.ui.theme.RedSecondary
import hu.jszf.marko.workoutplanner.ui.theme.UnknownMuscleRes
import hu.jszf.marko.workoutplanner.ui.theme.WhiteTranslucent

@Composable
fun PicPickerField(array: Array<Int>, valueId: Int?, onClick: (newValueId: Int?) -> Unit) {
    var isPickerDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val roundedShape = RoundedCornerShape(Dimensions.Roundness)

    if (isPickerDialogOpen) {
        Dialog(onDismissRequest = { isPickerDialogOpen = false }) {
            Card (
                shape = roundedShape,
                colors = CardDefaults.cardColors(containerColor = RedPrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.9f)
            ) {
                LazyColumn {
                    items(count = array.size) { index ->
                        Spacer(modifier = Modifier.height(5.dp))

                        Surface (
                            onClick = {
                                onClick(array[index])
                                isPickerDialogOpen = false
                            },
                            shape = roundedShape,
                            border = if (array[index] == valueId) BorderStroke(2.dp, FontColor) else null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.4f, false)
                                .padding(8.dp, 0.dp)
                        ) {
                            Image(
                                painter = painterResource(id = array[index]),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(roundedShape)
                            )
                        }

                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
        }
    }

    if (valueId == null || valueId == UnknownMuscleRes) {
        Button(
            onClick = { isPickerDialogOpen = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp),
            shape = roundedShape,
            modifier = Modifier
                .fillMaxWidth()
                .height(275.dp)
                .clip(roundedShape)
                .background(WhiteTranslucent)
                .border(Dimensions.BorderThickness, RedSecondary, roundedShape)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = UnknownMuscleRes),
                    contentDescription = "unknown_muscle_res",
                    contentScale = ContentScale.FillWidth,
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

        return
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(275.dp)
            .clip(roundedShape)
            .background(WhiteTranslucent)
            .border(Dimensions.BorderThickness, RedSecondary, roundedShape)
    ) {
        Image(
            painter = painterResource(id = valueId),
            contentDescription = "well_known_muscle_res",
            contentScale = ContentScale.FillWidth,
            alpha = 0.65f,
            modifier = Modifier.align(Alignment.Center)
        )
        IconButton(
            onClick = { onClick(null) },
            modifier = Modifier.align(Alignment.TopEnd),
            colors = IconButtonDefaults.iconButtonColors(contentColor = FontColor)
        ) {
            Icon(
                imageVector = Icons.Rounded.Clear,
                contentDescription = null,
                modifier = Modifier.size(Dimensions.IconSize)
            )
        }
    }
}

@Preview
@Composable
fun PicPickerFieldPreview() {
    PicPickerField(array = arrayOf(R.drawable.muscles_unknown), valueId = R.drawable.muscles_unknown, onClick = {})
}