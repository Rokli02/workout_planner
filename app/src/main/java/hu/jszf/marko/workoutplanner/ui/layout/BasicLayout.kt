package hu.jszf.marko.workoutplanner.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.presentation.NavigatorViewModel
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.theme.RedPrimary

@Composable
fun BasicLayout(
    modifier: Modifier = Modifier,
    extraIcons: @Composable() (RowScope.() -> Unit) = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable() (ColumnScope.() -> Unit)
) {
    val navVm = viewModel<NavigatorViewModel>(factory = WorkoutApplication.appModule.navigatorViewModelFactory)

    Scaffold (
        modifier = modifier
            .fillMaxSize()
            .background(RedPrimary),
        topBar = {
            Row (
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp),
            ) {
                IconButton(
                    onClick = {
                        navVm.navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(Dimensions.IconSize)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                extraIcons()
            }
        },
        bottomBar = bottomBar,
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            content()
        }

    }
}

@Preview
@Composable
fun BasicLayoutPreview() {
    BasicLayout {
        Text(text = "Nice")
    }
}