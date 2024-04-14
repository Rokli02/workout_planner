package hu.jszf.marko.workoutplanner.ui.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SnacbarViewModel: ViewModel() {
    internal val snackbarHostState = SnackbarHostState()
    var type: MutableStateFlow<SnackbarType> = MutableStateFlow(SnackbarType.Default)
        private set

    fun show(text: String, type: SnackbarType = SnackbarType.Default) {
        snackbarHostState.currentSnackbarData?.dismiss()
        this.type.update { type }

        viewModelScope.launch {
            snackbarHostState.showSnackbar(message = text, withDismissAction = true, duration = SnackbarDuration.Short)
        }
    }
}

enum class SnackbarType{
    Success,
    Fail,
    Default
}