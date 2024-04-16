package hu.jszf.marko.workoutplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import hu.jszf.marko.workoutplanner.presentation.NavigatorViewModel
import hu.jszf.marko.workoutplanner.presentation.Screen
import hu.jszf.marko.workoutplanner.presentation.activityToday.activityTodayScreenGraphComposable
import hu.jszf.marko.workoutplanner.presentation.allActivity.allActivityScreenGraphComposable
import hu.jszf.marko.workoutplanner.presentation.allExercise.allExerciseScreenGraphComposable
import hu.jszf.marko.workoutplanner.presentation.createActivity.createActivityScreenGraphcomposable
import hu.jszf.marko.workoutplanner.presentation.createExercise.createExerciseScreenGraphcomposable
import hu.jszf.marko.workoutplanner.presentation.exercise.exerciseScreenGraphComposable
import hu.jszf.marko.workoutplanner.presentation.home.homeScreenGraphComposable
import hu.jszf.marko.workoutplanner.presentation.workoutActivity.workoutActivityScreenGraphComposable
import hu.jszf.marko.workoutplanner.ui.snackbar.SnackbarView
import hu.jszf.marko.workoutplanner.ui.theme.RedPrimary
import hu.jszf.marko.workoutplanner.ui.theme.WorkoutPlannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutApplication.appModule.globalVMStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)

            WorkoutPlannerTheme {
                SnackbarView {
                    val navVM = viewModel<NavigatorViewModel>(factory = WorkoutApplication.appModule.navigatorViewModelFactory)
                    val navController = rememberNavController()

                    navVM.updateNavController(navController)
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route,
                        enterTransition = { fadeIn(tween(durationMillis = 400)) },
                        exitTransition = { fadeOut(tween(durationMillis = 400)) },
                        modifier = Modifier
                            .fillMaxSize()
                            .background(RedPrimary)
                    ) {
                        homeScreenGraphComposable(navController)
                        createActivityScreenGraphcomposable(navController)
                        activityTodayScreenGraphComposable(navController)
                        workoutActivityScreenGraphComposable(navController)
                        allActivityScreenGraphComposable(navController)
                        createExerciseScreenGraphcomposable(navController)
                        allExerciseScreenGraphComposable(navController)
                        exerciseScreenGraphComposable(navController)
                    }
                }
            }
        }
    }
}
