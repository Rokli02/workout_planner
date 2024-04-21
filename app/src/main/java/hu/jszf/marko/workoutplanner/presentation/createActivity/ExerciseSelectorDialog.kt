package hu.jszf.marko.workoutplanner.presentation.createActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import hu.jszf.marko.workoutplanner.WorkoutApplication
import hu.jszf.marko.workoutplanner.model.Exercise
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.component.CustomButton
import hu.jszf.marko.workoutplanner.ui.component.ExerciseView
import hu.jszf.marko.workoutplanner.ui.component.InputField
import hu.jszf.marko.workoutplanner.ui.component.LoadingIndicator
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import hu.jszf.marko.workoutplanner.ui.theme.RedPrimary
import hu.jszf.marko.workoutplanner.ui.theme.SuccessGreenPrimary
import hu.jszf.marko.workoutplanner.ui.theme.SuccessGreenSecondary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun ExerciseSelectorDialogView(
    closeDialog: (exercises: List<Exercise>?) -> Unit,
    alreadySelected: List<Long>,
) {
    val selectorVM = WorkoutApplication.appModule.getExerciseSelectorViewModel()
    selectorVM.alreadySelected = alreadySelected

    val selectedItems = selectorVM.selectedItems
    val filter by selectorVM.filter.collectAsState()

    ExerciseSelectorDialog(
        exercisesFlow = selectorVM.exercises,
        closeDialog = {
            closeDialog(it)
            selectorVM.clearSelectedItems()
        },
        filter = filter,
        onFilterChange = selectorVM::updateFilter,
        selectedItems = selectedItems,
        updateSelectedItems = selectorVM::updateSelectedItems,
        getExercisesByIds = selectorVM::getExercisesByIds
    )
}

@Composable
fun ExerciseSelectorDialog(
    exercisesFlow: Flow<PagingData<Exercise>>,
    closeDialog: (exercises: List<Exercise>?) -> Unit,
    filter: String,
    onFilterChange: (String) -> Unit,
    selectedItems: MutableMap<Long, Boolean>,
    updateSelectedItems: (Long) -> Unit,
    getExercisesByIds: suspend (List<Long>) -> List<Exercise>,
) {
    Dialog( onDismissRequest = {
        closeDialog(null)
    } ) {
        val crScope = rememberCoroutineScope()
        val exercises = exercisesFlow.collectAsLazyPagingItems()

        Card (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.9f)
        ) {
            Scaffold (
                containerColor = RedPrimary,
                contentColor = FontColor,
                topBar = {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                             InputField(
                            value = filter,
                            onChange = onFilterChange,
                            placeholder = { Text("Gyakorlat keresés") },
                            trailingIcon = { Icon(imageVector = Icons.Rounded.Search, contentDescription = null) },
                            modifier = Modifier
                                .fillMaxWidth(.95f)
                                .padding(0.dp, 10.dp)
                        )
                        Box(modifier = Modifier
                            .fillMaxWidth(.95f)
                            .height(Dimensions.BorderThickness)
                            .background(Color.Black))
                        Spacer(modifier = Modifier.height(Dimensions.HalfElementGap))
                    }
                },
                floatingActionButtonPosition = FabPosition.End,
                floatingActionButton = {
                    var isBtnEnabled by rememberSaveable {
                        mutableStateOf(true)
                    }

                    IconButton(
                        enabled = isBtnEnabled,
                        onClick = {
                            isBtnEnabled = false

                            crScope.launch {
                                val ids = selectedItems
                                    .filter{ it.value }
                                    .map { (key, _) ->
                                        key
                                    }

                                val result = getExercisesByIds(ids)

                                isBtnEnabled = true
                                closeDialog(result)
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = SuccessGreenPrimary,
                            contentColor = SuccessGreenSecondary,
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Hozzáadás",
                            modifier = Modifier.size(Dimensions.IconSize),
                        )
                    }
                }
                ) { inheritedPadding ->
                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(inheritedPadding)
                ) {
                    items(
                        count = exercises.itemCount,
                        key = exercises.itemKey { exercise ->
                            "${exercise.id}_${selectedItems[exercise.id]}"
                        }
                    ) { index ->
                        val modifier = remember(selectedItems[exercises[index]!!.id]) {
                            if (selectedItems[exercises[index]!!.id] == true) {
                                Modifier
                                    .padding(Dimensions.ScreenPaddigInline, 0.dp)
                                    .border(
                                        Dimensions.BorderThickness,
                                        Color.White,
                                        RoundedCornerShape(Dimensions.Roundness)
                                    )
                            } else Modifier.padding(Dimensions.ScreenPaddigInline, 0.dp)
                        }

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimensions.HalfElementGap)
                        )

                        ExerciseView(
                            exercise = exercises[index]!!,
                            modifier = modifier,
                        ) {
                            updateSelectedItems(exercises[index]!!.id!!)
                            exercises.refresh()
                        }

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimensions.HalfElementGap)
                        )
                    }
                    when (exercises.loadState.refresh) {
                        LoadState.Loading -> Unit

                        is LoadState.Error -> {
                            item {
                                CustomButton(content = {
                                    Icon(
                                        Icons.Rounded.Refresh,
                                        null,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }) {
                                    exercises.refresh()
                                }
                            }
                        }

                        is LoadState.NotLoading -> {
                            if (exercises.itemCount < 1) {
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
                    when (exercises.loadState.append) {
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
                                    exercises.retry()
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

@Preview
@Composable
fun ExerciseSelectorDialogPreview() {
    ExerciseSelectorDialog(
        exercisesFlow = MutableStateFlow(PagingData.empty()),
        closeDialog = {},
        filter = "",
        onFilterChange = {},
        selectedItems = mutableMapOf(),
        updateSelectedItems = {},
        getExercisesByIds = { listOf() }
    )
}