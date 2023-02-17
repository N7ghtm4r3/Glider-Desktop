package layouts.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import helpers.primaryColor
import helpers.redColor

/**
 * Custom layout for a [OutlinedTextField]
 *
 * @param isError whether the text field is in an error state
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction]
 * @param visualTransformation transforms the visual representation of the input [value]
 * @param text Text to display
 * @param value Modifier to be applied to the text
 * @param text Text to display
 * @param value Modifier to be applied to the text
 * @param onChange Text to display
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field container
 * @param leadingOnClick action to perform when [leadingIcon] is clicked
 * @param trailingIcon size of the text, default 15[sp]
 * @param trailingOnClick action to perform when [trailingIcon] is clicked
 * @param modifier a [Modifier] for this text field
 */
@Composable
fun GliderTextField(
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    text: String,
    value: String,
    onChange: (String) -> Unit,
    leadingIcon: ImageVector,
    leadingOnClick: (() -> Unit) = {},
    trailingIcon: ImageVector,
    trailingOnClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = primaryColor,
            focusedBorderColor = redColor,
            unfocusedBorderColor = primaryColor,
            focusedLabelColor = primaryColor,
            unfocusedLabelColor = primaryColor,
            cursorColor = primaryColor,
            trailingIconColor = primaryColor,
            errorBorderColor = redColor,
            errorTrailingIconColor = redColor
        ),
        singleLine = true,
        isError = isError,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        leadingIcon = {
            IconButton(
                onClick = leadingOnClick
            ) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = primaryColor
                )
            }
        },
        trailingIcon = {
            IconButton(
                onClick = trailingOnClick
            ) {
                Icon(
                    imageVector = if (!isError) trailingIcon else Icons.Default.Error,
                    contentDescription = null,
                )
            }
        },
        label = { Text(text = text) },
        value = value,
        onValueChange = onChange
    )
}