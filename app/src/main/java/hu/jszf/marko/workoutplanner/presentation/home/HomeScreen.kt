package hu.jszf.marko.workoutplanner.presentation.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.presentation.Screen
import hu.jszf.marko.workoutplanner.presentation.viewModelFactory
import hu.jszf.marko.workoutplanner.ui.Dimensions

@Composable
fun HomeScreen() {
    var isSidebarOpened by rememberSaveable {
        mutableStateOf(false)
    }
    val corutineScope = rememberCoroutineScope()
    val homeViewModel = viewModel<HomeViewModel>(factory = viewModelFactory { HomeViewModel(WorkoutApplication.appModule.workoutActivityRepository) })

    LaunchedEffect(corutineScope) {
        homeViewModel.getLastActivities()
    }

    Scaffold (
        topBar = {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                IconButton(
                    onClick = {
                        isSidebarOpened = !isSidebarOpened
                    }
                ) {
                    val rotationgDegree by animateFloatAsState(
                        label = "sidebar_toggle_rotation_anim",
                        targetValue = if (isSidebarOpened) -180f else 0f,
                        animationSpec = tween(durationMillis = 250, easing = LinearEasing)
                    )
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(Dimensions.IconSize)
                            .rotate(rotationgDegree)
                    )
                }
            }
        }
    ) {inheritedPadding ->
        AnimatedContent(
            targetState = isSidebarOpened,
            label = "sidebar_open_anim",
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { if (isSidebarOpened) it else -it },
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { if (isSidebarOpened) -it else it }
                )
            },
        ) {
            if (it) {
                Box(modifier = Modifier.padding(inheritedPadding)) {
                    SidebarView()
                }
            } else {
                Box(modifier = Modifier.padding(inheritedPadding)) {
                    val lastActivities by homeViewModel.lastActivities.collectAsState(initial = null)

                    HomeView(lastActivities)
                }
            }
        }
    }
}

fun NavGraphBuilder.homeScreenGraphComposable(navController: NavHostController) {
    composable(Screen.HomeScreen.route) {
        HomeScreen()
    }
}