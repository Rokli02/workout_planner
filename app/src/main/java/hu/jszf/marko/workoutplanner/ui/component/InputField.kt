package hu.jszf.marko.workoutplanner.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.jszf.marko.workoutplanner.ui.Dimensions
import hu.jszf.marko.workoutplanner.ui.theme.FontColor
import hu.jszf.marko.workoutplanner.ui.theme.FontColorDark
import hu.jszf.marko.workoutplanner.ui.theme.RedPrimary
import hu.jszf.marko.workoutplanner.ui.theme.RedSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    value: String,
    onChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    textStyle: TextStyle? = null
    ,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    ),
    lines: Int = 1,
) {
    Column(modifier = modifier) {
        if (label != null) {
            Text(
                text = label,
                color = FontColor,
                fontSize = Dimensions.InputFieldLabelSize,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        val interactionSource = remember { MutableInteractionSource() }
        val txtFieldShape = RoundedCornerShape(8.dp)

        Spacer(Modifier.height(4.dp))

//        BasicTextField(
//            value = value,
//            onValueChange = onChange,
//            singleLine = lines == 1,
//            minLines = lines,
//            textStyle = TextStyle(
//                color = FontColorDark,
//                fontSize = 16.sp,
//            ),
//            interactionSource = interactionSource,
//            cursorBrush = SolidColor(FontColorDark),
//            modifier = Modifier
//                .fillMaxWidth()
//                .defaultMinSize(
//                    minHeight = 48.dp
//                )
//                .clip(txtFieldShape)
//                .background(Color.White)
//                .border(Dimensions.BorderThickness, RedSecondary, txtFieldShape)
//                .align(Alignment.CenterHorizontally)
//                .padding(12.dp, 12.dp),
//        )

        BasicTextField(
            value = value,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(
                    minHeight = 48.dp
                )
                .clip(txtFieldShape)
                .border(Dimensions.BorderThickness, RedSecondary, txtFieldShape),
            onValueChange = onChange,
            enabled = true,
            readOnly = false,
            textStyle = if (textStyle == null) LocalTextStyle.current else LocalTextStyle.current.merge(textStyle),
            cursorBrush = SolidColor(FontColorDark),
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default,
            interactionSource = interactionSource,
            singleLine = lines == 1,
            maxLines = if (lines == 1) 1 else Int.MAX_VALUE,
            minLines = lines,
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = value,
                    visualTransformation = VisualTransformation.None,
                    innerTextField = innerTextField,
                    placeholder = placeholder,
//                    label = label,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    prefix = prefix,
                    suffix = suffix,
                    supportingText = supportingText,
                    shape = txtFieldShape,
                    singleLine = lines == 1,
                    enabled = true,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    val value by remember {
        mutableStateOf("Itt származik vanali érték")
    }

    InputField(
        value = value,
        onChange = {},
        placeholder = { Text("Gyóóó") },
        modifier = Modifier
            .fillMaxWidth()
            .background(RedPrimary)
    )
}