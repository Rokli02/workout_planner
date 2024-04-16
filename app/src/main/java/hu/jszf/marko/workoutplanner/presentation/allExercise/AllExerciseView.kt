package hu.jszf.marko.workoutplanner.presentation.allExercise

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import hu.jszf.marko.workoutplanner.model.Exercise
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.component.CustomButton
import hu.jszf.marko.workoutplanner.ui.component.ExerciseView
import hu.jszf.marko.workoutplanner.ui.component.InputField
import hu.jszf.marko.workoutplanner.ui.component.LoadingIndicator
import hu.jszf.marko.workoutplanner.ui.layout.BasicLayout
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun AllExerciseView(
    filter: String,
    onChange: (String) -> Unit,
    exercisesLPI: LazyPagingItems<Exercise>,
) {
    BasicLayout {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimensions.ScreenPaddigInline, Dimensions.ScreenPaddigBlock)
        ) {
            InputField(
                value = filter,
                onChange = onChange,
                placeholder = { Text(text = "Keresés a gyakorlatok között") },
                trailingIcon = { Icon(Icons.Rounded.Search, null) },
            )

            Spacer(modifier = Modifier.height(Dimensions.HalfElementGap))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.BorderThickness)
                    .background(Color.Black)
            )

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                when (exercisesLPI.loadState.refresh) {
                    LoadState.Loading -> {
                        item {
                            LoadingIndicator(modifier = Modifier.fillMaxWidth())
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            CustomButton(content = {
                                Icon(
                                    Icons.Rounded.Refresh,
                                    null,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }) {
                                exercisesLPI.refresh()
                            }
                        }
                    }

                    is LoadState.NotLoading -> {
                        if (exercisesLPI.itemCount < 1) {
                            item {
                                Text(
                                    text = "Még nincs felvett gyakorlatod, hozz létre egyet!",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }
                        }
                    }
                }
                items(
                    count = exercisesLPI.itemCount,
                    key = exercisesLPI.itemKey { it.id!! },
                    contentType = exercisesLPI.itemContentType { "Exercises" }
                ) { index ->
                    exercisesLPI[index]?.also {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimensions.HalfElementGap)
                        )
                        ExerciseView(exercise = it)
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimensions.HalfElementGap)
                        )
                    }
                }
                when (exercisesLPI.loadState.append) {
                    LoadState.Loading -> {
                        item {
                            LoadingIndicator(modifier = Modifier.fillMaxWidth())
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            CustomButton(content = {
                                Icon(
                                    Icons.Rounded.Refresh,
                                    null,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }) {
                                exercisesLPI.retry()
                            }
                        }
                    }

                    is LoadState.NotLoading -> Unit
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllExerciseViewPreview() {
    AllExerciseView(
        filter = "Ez egy filter",
        onChange = {},
        exercisesLPI = MutableStateFlow(PagingData.empty<Exercise>()).collectAsLazyPagingItems()
    )
}