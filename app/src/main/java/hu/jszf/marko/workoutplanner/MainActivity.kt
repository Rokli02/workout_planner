package hu.jszf.marko.workoutplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.jszf.marko.workoutplanner.presentation.NavigatorViewModel
import hu.jszf.marko.workoutplanner.presentation.Screen
import hu.jszf.marko.workoutplanner.presentation.activityToday.ActivityTodayScreen
import hu.jszf.marko.workoutplanner.presentation.createActivity.CreateActivityScreen
import hu.jszf.marko.workoutplanner.presentation.home.HomeScreen
import hu.jszf.marko.workoutplanner.presentation.workoutActivity.WorkoutActivityScreen
import hu.jszf.marko.workoutplanner.ui.snackbar.SnackbarView
import hu.jszf.marko.workoutplanner.ui.theme.RedPrimary
import hu.jszf.marko.workoutplanner.ui.theme.WorkoutPlannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutPlannerTheme {
                val navVM = viewModel<NavigatorViewModel>(factory = WorkoutApplication.appModule.navigatorViewModelFactory)
                val navController = rememberNavController()

                navVM.updateNavController(navController)

                SnackbarView {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(RedPrimary)
                    ) {
                        composable(Screen.HomeScreen.route) {
                            HomeScreen()
                        }
                        composable(route = "${Screen.CreateActivityScreen.route}?workoutActivityId={workoutActivityId}", arguments = listOf(
                            navArgument(name = "workoutActivityId") {
                                type = NavType.LongType
                                defaultValue = -1L
                                nullable = false
                            }
                        )) {
                            CreateActivityScreen(it.arguments?.getLong("workoutActivityId"))
                        }
                        composable(Screen.ActivityTodayScreen.route) {
                            ActivityTodayScreen()
                        }
                        composable(route = "${Screen.WorkoutActivityScreen.route}/{workoutActivityId}", arguments = listOf(
                            navArgument(name = "workoutActivityId") {
                                type = NavType.LongType
                                nullable = false
                            }
                        )) {
                            if (it.arguments?.getLong("workoutActivityId")?.let {id ->
                                    WorkoutActivityScreen(workoutActivityId = id)

                                    0
                            } != 0) navController.navigate(Screen.HomeScreen.route)
                        }
                    }
                }
            }
        }
    }
}
