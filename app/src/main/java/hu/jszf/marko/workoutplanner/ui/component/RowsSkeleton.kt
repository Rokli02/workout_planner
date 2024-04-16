package hu.jszf.marko.workoutplanner.ui.component

import android.annotation.SuppressLint
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hu.jszf.marko.workoutplanner.ui.theme.WhiteTranslucent

@Composable
fun RowSkeleton(@SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxWidth().height(80.dp), paddingBlock: Dp = 8.dp) {
    val transparency = rememberInfiniteTransition("Row_skeleton_trans_anim")
    val opacityAnim by transparency.animateFloat(
        label = "opacity_change_of_skeleton_anim",
        initialValue = .2f,
        targetValue = .05f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(
                durationMillis = 4000,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        )
    )

    val bgColor = WhiteTranslucent.copy(alpha = opacityAnim)
    val shape = RoundedCornerShape(8.dp)

    Spacer(modifier = Modifier.height(paddingBlock))
    Box(modifier = modifier.clip(shape).background(bgColor))
    Spacer(modifier = Modifier.height(paddingBlock))
}

@Preview
@Composable
fun RowSkeletonPreview() {
    RowSkeleton()
}