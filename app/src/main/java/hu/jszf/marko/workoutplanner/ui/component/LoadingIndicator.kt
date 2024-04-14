package hu.jszf.marko.workoutplanner.ui.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import hu.jszf.marko.workoutplanner.ui.theme.FontColor

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 32.sp,
    color: Color = FontColor,
    durationInMillis: Int = 3000,
) {
    val dotAnim = rememberInfiniteTransition("infinite_loading_dot_anim")
    val dotNumber by dotAnim.animateFloat(
        initialValue = 0f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationInMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "loading_dot_anim"
    )

    val sb = StringBuilder()
    for (i in 0..dotNumber.toInt()) {
        sb.append(".")
    }

    Text(
        text = "Loading$sb",
        textAlign = TextAlign.Center,
        modifier = modifier,
        fontSize = fontSize,
        color = color,
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingIndicatorPreview() {
    LoadingIndicator()
}