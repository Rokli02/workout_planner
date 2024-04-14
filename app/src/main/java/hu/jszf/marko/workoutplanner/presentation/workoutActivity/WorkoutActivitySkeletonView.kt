package hu.jszf.marko.workoutplanner.presentation.workoutActivity

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.layout.BasicLayout
import hu.jszf.marko.workoutplanner.ui.theme.WhiteTranslucent

@Composable
fun WorkoutActivitySkeletonView() {
    BasicLayout {
        val transparency = rememberInfiniteTransition("WorkoutActivitySkeleton_trans_anim")
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

        Column (
            modifier = Modifier.
                fillMaxSize()
                .padding(
                    Dimensions.ScreenPaddigInline,
                    0.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val bgColor = WhiteTranslucent.copy(alpha = opacityAnim)
            val shape = RoundedCornerShape(8.dp)

            Box(modifier = Modifier.fillMaxWidth().height(275.dp).clip(shape).background(bgColor))
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth(.5f).height(50.dp).clip(shape).background(bgColor))
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(.9f).height(50.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                Box(modifier = Modifier.weight(1f).fillMaxHeight().clip(shape).background(bgColor).padding(0.dp, 4.dp, 0.dp, 4.dp))
                Spacer(modifier = Modifier.weight(.3f))
                Box(modifier = Modifier.weight(1f).fillMaxHeight().clip(shape).background(bgColor).padding(0.dp, 4.dp, 0.dp, 4.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().height(Dimensions.BorderThickness).background(Color.Black))
            LazyColumn {
                items(3) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(80.dp).clip(shape).background(bgColor))
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutActivitySkeletonViewPreview() {
    WorkoutActivitySkeletonView()
}