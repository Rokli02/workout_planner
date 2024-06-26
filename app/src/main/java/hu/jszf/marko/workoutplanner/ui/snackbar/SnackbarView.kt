package hu.jszf.marko.workoutplanner.ui.snackbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.ui.theme.BlackTranslucent
import hu.jszf.marko.workoutplanner.ui.theme.FailRedPrimary
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import hu.jszf.marko.workoutplanner.ui.theme.FontColorMisc
import hu.jszf.marko.workoutplanner.ui.theme.SuccessGreenPrimary
import hu.jszf.marko.workoutplanner.ui.theme.SuccessGreenSecondary

@Composable
fun SnackbarView( content: @Composable (PaddingValues) -> Unit) {
    val snackbarViewModel = WorkoutApplication.appModule.getSnackbarViewModel()

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarViewModel.snackbarHostState,
                snackbar = {
                    val type by snackbarViewModel.type.collectAsState()

                    val sbColors = when (type) {
                        SnackbarType.Default -> listOf(
                            SnackbarDefaults.color,
                            SnackbarDefaults.contentColor,
                            SnackbarDefaults.dismissActionContentColor,
                            SnackbarDefaults.actionColor,
                            SnackbarDefaults.actionContentColor,
                        )
                        SnackbarType.Success -> SnackbarSuccessColors
                        SnackbarType. Fail -> SnackbarFailColors
                    }

                    Snackbar(
                        snackbarData = it,
                        containerColor = sbColors[0],
                        contentColor = sbColors[1],
                        dismissActionContentColor = sbColors[2],
                        actionColor = sbColors[3],
                        actionContentColor = sbColors[4],
                    )
                }
            )
        }
    ) {
        content(it)
    }
}

val SnackbarSuccessColors = listOf(
    SuccessGreenPrimary,
    FontColor,
    FontColor,
    SuccessGreenSecondary,
    BlackTranslucent,
)
val SnackbarFailColors = listOf(
    FailRedPrimary,
    FontColor,
    FontColor,
    FontColorMisc,
    BlackTranslucent,
)


@Preview(showBackground = true)
@Composable
fun SnackbarViewPreview() {
    val colorList = SnackbarSuccessColors

    Snackbar(
        snackbarData = object: SnackbarData{
            override val visuals: SnackbarVisuals = object: SnackbarVisuals {
                override val actionLabel: String
                    get() = "big action"
                override val duration: SnackbarDuration
                    get() = SnackbarDuration.Indefinite
                override val message: String
                    get() = "szép meszidzs"
                override val withDismissAction: Boolean
                    get() = true
            }

            override fun dismiss() {}

            override fun performAction() {}
        },
        containerColor = colorList[0],
        contentColor = colorList[1],
        dismissActionContentColor = colorList[2],
        actionColor = colorList[3],
        actionContentColor = colorList[4],
    )
}