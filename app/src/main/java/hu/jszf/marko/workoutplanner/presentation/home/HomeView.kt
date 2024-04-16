package hu.jszf.marko.workoutplanner.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.List
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.model.WorkoutActivity
import hu.jszf.marko.workoutplanner.presentation.NavigatorViewModel
import hu.jszf.marko.workoutplanner.presentation.Screen
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.blockBorder
import hu.jszf.marko.workoutplanner.ui.component.RowSkeleton
import hu.jszf.marko.workoutplanner.ui.component.WorkoutActivityView
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import hu.jszf.marko.workoutplanner.ui.theme.FontColorDark
import hu.jszf.marko.workoutplanner.ui.theme.LogoLevelsRes
import hu.jszf.marko.workoutplanner.ui.theme.RedSecondary
import hu.jszf.marko.workoutplanner.ui.theme.RedVibrant
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
internal fun HomeView(lastActivities: List<WorkoutActivity>?) {
    val navVM = viewModel<NavigatorViewModel>(factory = WorkoutApplication.appModule.navigatorViewModelFactory)

    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .padding(
                Dimensions.ScreenPaddigInline,
                0.dp,
            ),
        bottomBar = {
            Box (modifier = Modifier.fillMaxWidth()) {
                Row (
                    modifier = Modifier
                        .background(RedSecondary, RoundedCornerShape(140f, 140f, 0f, 0f))
                        .align(Alignment.BottomCenter)
                        .padding(12.dp, 4.dp)
                ) {
                    Button(
                        onClick = {
                            navVM.navController.navigate(Screen.AllActivityScreen.route)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = FontColorDark),
                    ) {
                        Icon(Icons.Sharp.List, null, modifier = Modifier.size(32.dp))
                    }

                    Spacer(modifier = Modifier
                        .padding(6.dp, 8.dp, 6.dp, 0.dp)
                        .width(1.dp)
                        .height(38.dp)
                        .background(Color.Black))

                    Button(
                        onClick = {
                            navVM.navController.navigate(Screen.ActivityTodayScreen.route)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = FontColorDark),
                    ) {
                        Icon(Icons.Sharp.Person, null, modifier = Modifier.size(32.dp))
                    }

                    Spacer(modifier = Modifier
                        .padding(6.dp, 8.dp, 6.dp, 0.dp)
                        .width(1.dp)
                        .height(38.dp)
                        .background(Color.Black))
                    Button(
                        onClick = {
                            navVM.navController.navigate(Screen.CreateActivityScreen.route)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = FontColorDark),
                    ) {
                        Icon(Icons.Sharp.Add, null, modifier = Modifier.size(32.dp))
                    }
                }
            }
        }
    ) {
        Column (
            modifier = Modifier.padding(it)
        ) {
            Image(
                painter = painterResource(id = LogoLevelsRes[0]),
                contentDescription = "workout logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        0.dp,
                        Dimensions.HalfElementGap,
                    )
                    .blockBorder(Dimensions.BorderThickness, FontColorDark),
            )

            LazyColumn (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        0.dp,
                        Dimensions.HalfElementGap,
                        0.dp,
                        Dimensions.HalfElementGap,
                    )
                    .blockBorder(Dimensions.BorderThickness, FontColorDark)
            ) {
                when {
                    lastActivities == null -> {
                        items(GetLastXWOActivity) {
                            RowSkeleton()
                        }

                        item {
                            RowSkeleton(modifier = Modifier
                                .width(160.dp)
                                .height(40.dp)
                                .padding(0.dp, 4.dp, 0.dp, 4.dp))
                        }
                    }
                    lastActivities.isNotEmpty() -> {
                        items(lastActivities.size) {
                            WorkoutActivityView(
                                workoutActivity = lastActivities[it],
                                modifier = Modifier
                                    .height(96.dp)
                                    .padding(0.dp, 8.dp, 0.dp, 8.dp)
                            )
                        }

                        item {
                            Button(
                                onClick = { navVM.navController.navigate(Screen.AllActivityScreen.route) },
                                colors = ButtonDefaults.buttonColors(containerColor = RedVibrant, contentColor = FontColor),
                                contentPadding = PaddingValues(
                                    horizontal = 12.dp,
                                    vertical = 0.dp,
                                ),
                                modifier = Modifier.padding(0.dp, 4.dp, 0.dp, 4.dp)
                            ) {
                                Text(text = "Összes gyakorlat megtekintése")
                            }
                        }
                    }
                    else -> {
                        item {
                            Text(
                                text = "Még nincs felvett gyakorlatod, hozz létre egyet!",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 12.dp),
                            )
                        }
                    }
                }
            }
        }
    }
//    Box (modifier = Modifier
//        .fillMaxSize()
//        .padding(
//            Dimensions.ScreenPaddigInline,
//            0.dp,
//        )
//    ) {
//        Column (
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//        ) {
//            Image(
//                painter = painterResource(id = LogoLevelsRes[0]),
//                contentDescription = "workout logo",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(
//                        0.dp,
//                        Dimensions.HalfElementGap,
//                    )
//                    .blockBorder(Dimensions.BorderThickness, FontColorDark),
//            )
//
//            LazyColumn (
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(
//                        0.dp,
//                        Dimensions.HalfElementGap,
//                        0.dp,
//                        80.dp,
//                    )
//                    .blockBorder(Dimensions.BorderThickness, FontColorDark)
//            ) {
//                when {
//                    lastActivities == null -> {
//                        items(GetLastXWOActivity) {
//                            RowSkeleton()
//                        }
//
//                        item {
//                            RowSkeleton(modifier = Modifier
//                                .width(160.dp)
//                                .height(40.dp))
//                        }
//                    }
//                    lastActivities.isNotEmpty() -> {
//                        items(lastActivities.size) {
//                            WorkoutActivityView(
//                                workoutActivity = lastActivities[it],
//                                modifier = Modifier
//                                    .height(96.dp)
//                                    .padding(0.dp, 8.dp, 0.dp, 8.dp)
//                            )
//                        }
//
//                        item {
//                            Button(
//                                onClick = { navVM.navController.navigate(Screen.AllActivityScreen.route) },
//                                colors = ButtonDefaults.buttonColors(containerColor = RedVibrant, contentColor = FontColor),
//                                contentPadding = PaddingValues(
//                                    horizontal = 12.dp,
//                                    vertical = 0.dp,
//                                ),
//                                modifier = Modifier.padding(0.dp, 4.dp, 0.dp, 16.dp)
//                            ) {
//                                Text(text = "Összes gyakorlat megtekintése")
//                            }
//                        }
//                    }
//                    else -> {
//                        item {
//                            Text(
//                                text = "Még nincs felvett gyakorlatod, hozz létre egyet!",
//                                textAlign = TextAlign.Center,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(0.dp, 12.dp),
//                            )
//                        }
//                    }
//                }
//            }
//        }
//        Row (
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .background(RedSecondary, RoundedCornerShape(140f, 140f, 0f, 0f))
//                .padding(12.dp, 4.dp)
//        ) {
//            Button(
//                onClick = {
//                      navVM.navController.navigate(Screen.AllActivityScreen.route)
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = FontColorDark),
//            ) {
//                Icon(Icons.Sharp.List, null, modifier = Modifier.size(32.dp))
//            }
//
//            Spacer(modifier = Modifier
//                .padding(6.dp, 8.dp, 6.dp, 0.dp)
//                .width(1.dp)
//                .height(38.dp)
//                .background(Color.Black))
//
//            Button(
//                onClick = {
//                    navVM.navController.navigate(Screen.ActivityTodayScreen.route)
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = FontColorDark),
//            ) {
//                Icon(Icons.Sharp.Person, null, modifier = Modifier.size(32.dp))
//            }
//
//            Spacer(modifier = Modifier
//                .padding(6.dp, 8.dp, 6.dp, 0.dp)
//                .width(1.dp)
//                .height(38.dp)
//                .background(Color.Black))
//            Button(
//                onClick = {
//                    navVM.navController.navigate(Screen.CreateActivityScreen.route)
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = FontColorDark),
//            ) {
//                Icon(Icons.Sharp.Add, null, modifier = Modifier.size(32.dp))
//            }
//        }
//    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    val lastActivities: List<WorkoutActivity> by MutableStateFlow(listOf(
        WorkoutActivity(1, "Egyes edzés"),
        WorkoutActivity(2, "Durvább edzés"),
        WorkoutActivity(3, "Levezető"),
        WorkoutActivity(4, "Levezető After"),
    )).collectAsState()

    HomeView(lastActivities)
}