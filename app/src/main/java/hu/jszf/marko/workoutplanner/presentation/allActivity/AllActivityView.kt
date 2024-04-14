package hu.jszf.marko.workoutplanner.presentation.allActivity

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import hu.jszf.marko.workoutplanner.model.WorkoutActivity
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.component.CustomButton
import hu.jszf.marko.workoutplanner.ui.component.InputField
import hu.jszf.marko.workoutplanner.ui.component.LoadingIndicator
import hu.jszf.marko.workoutplanner.ui.component.WorkoutActivityView
import hu.jszf.marko.workoutplanner.ui.layout.BasicLayout
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
internal fun AllActivityView(
    filter: String,
    onChange: (filter: String) -> Unit,
    workoutActivitiesLPI: LazyPagingItems<WorkoutActivity>
) {
    BasicLayout {
        Scaffold (
            topBar = {
                InputField(
                    value = filter,
                    onChange = onChange,
                    trailingIcon = { Icon(Icons.Rounded.Search, null) },
                )
            },
            modifier = Modifier.padding(Dimensions.ScreenPaddigInline, Dimensions.ScreenPaddigBlock)
        ) { inheritedPadding ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(inheritedPadding)
            ) {
                Spacer(modifier = Modifier
                    .height(Dimensions.HalfElementGap)
                    .fillMaxWidth())
                Button(onClick = { workoutActivitiesLPI.refresh() }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                    Text("Refresh")
                }
                Spacer(modifier = Modifier
                    .height(Dimensions.HalfElementGap)
                    .fillMaxWidth())
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.BorderThickness)
                    .background(Color.Black))

                LazyColumn (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (workoutActivitiesLPI.loadState.refresh) {
                        LoadState.Loading -> {
                            item {
                                LoadingIndicator(modifier = Modifier.fillMaxWidth())
                            }
                        }
                        is LoadState.Error -> {
                            item {
                                CustomButton(content = {
                                    Icon(Icons.Rounded.Refresh, null, modifier = Modifier.fillMaxWidth())
                                }) {
                                    workoutActivitiesLPI.refresh()
                                }
                            }
                        }
                        is LoadState.NotLoading -> {
                            if (workoutActivitiesLPI.itemCount < 1) {
                                item {
                                    Text("Még nincs felvett gyakorlatod, hozz létre egyet!")
                                }
                            }
                        }
                    }
                    items(
                        count = workoutActivitiesLPI.itemCount,
                        key = workoutActivitiesLPI.itemKey { it.id!! },
                        contentType = workoutActivitiesLPI.itemContentType { "WorkoutActivities" }
                    ) { index ->
                        workoutActivitiesLPI[index]?.also {
                            Spacer(modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimensions.HalfElementGap))
                            WorkoutActivityView(workoutActivity = it)
                            Spacer(modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimensions.HalfElementGap))
                        }
                    }
                    when (workoutActivitiesLPI.loadState.append) {
                        LoadState.Loading -> {
                            item {
                                LoadingIndicator(modifier = Modifier.fillMaxWidth())
                            }
                        }

                        is LoadState.Error -> {
                            item {
                                CustomButton(content = {
                                    Icon(Icons.Rounded.Refresh, null, modifier = Modifier.fillMaxWidth())
                                }) {
                                    workoutActivitiesLPI.retry()
                                }
                            }
                        }
                        is LoadState.NotLoading -> Unit
                    }
                }
            }
        }
    }
}

@SuppressLint("FlowOperatorInvokedInComposition")
@Preview
@Composable
fun AllActivityViewPreview() {
    AllActivityView(
        filter = "mai",
        onChange = {},
        workoutActivitiesLPI = MutableStateFlow(PagingData.empty<WorkoutActivity>()).collectAsLazyPagingItems(),
    )
}