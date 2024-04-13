package hu.jszf.marko.workoutplanner.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.presentation.NavigatorViewModel
import hu.jszf.marko.workoutplanner.presentation.viewModelFactory
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.theme.RedPrimary

@Composable
fun BasicLayout(
    modifier: Modifier = Modifier,
    extraIcons: @Composable() (BoxScope.() -> Unit) = {},
    content: @Composable() (ColumnScope.() -> Unit)
) {
    val navVm = viewModel<NavigatorViewModel>(factory = WorkoutApplication.appModule.navigatorViewModelFactory)

    Box (
        modifier = modifier
            .fillMaxSize()
            .background(RedPrimary)
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

        extraIcons()

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 42.dp, 0.dp, 0.dp)
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