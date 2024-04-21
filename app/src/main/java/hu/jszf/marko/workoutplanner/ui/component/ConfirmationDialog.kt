package hu.jszf.marko.workoutplanner.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import hu.jszf.marko.workoutplanner.ui.theme.RedPrimary
import hu.jszf.marko.workoutplanner.ui.theme.SuccessGreenSecondary

@Composable
fun ConfirmationDialog(
    title: String,
    confirmationText: String = "Elfogadom",
    declineText: String = "Mégse",
    onConfirmation: () -> Unit,
    onDecline: () -> Unit,
    onClose: () -> Unit,
) {
    val roundedShape = RoundedCornerShape(Dimensions.Roundness)

    Dialog(onDismissRequest = onClose) {
        Surface (
            modifier = Modifier.fillMaxWidth(),
            shape = roundedShape,
            color = RedPrimary,
        ) {
            Column (
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 4.dp, 4.dp, 0.dp)
                ) {
                    Text(title, fontSize = 18.sp, color = FontColor, modifier = Modifier.padding(4.dp, 6.dp, 4.dp, 0.dp))

                    IconButton(
                        modifier = Modifier.size(Dimensions.IconSize),
                        onClick = onClose
                    ) {
                        Icon(imageVector = Icons.Rounded.Close, contentDescription = "Mégse")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row (
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.padding(12.dp, 6.dp)
                ) {
                    CustomButton(text = confirmationText, onClick = { onConfirmation(); onClose() }, backgroundColor = Color.Transparent, fontColor = SuccessGreenSecondary, modifier = Modifier.weight(1f) )
                    Spacer(modifier = Modifier.width(10.dp))
                    CustomButton(text = declineText, onClick = { onDecline(); onClose() }, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview
@Composable
fun ConfirmationDialogPreview() {
    ConfirmationDialog(
        title = "Elfogadod-e?",
        onConfirmation = {},
        onDecline = {},
        onClose = {}
    )
}