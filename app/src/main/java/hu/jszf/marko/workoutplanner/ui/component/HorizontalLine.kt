package hu.jszf.marko.workoutplanner.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import hu.jszf.marko.workoutplanner.ui.Dimensions

@Composable
fun HorizontalLine(modifier: Modifier = Modifier, color: Color = Color.Black) {
    Column(modifier = modifier.fillMaxWidth().height(Dimensions.BorderThickness).background(color)) {}
}