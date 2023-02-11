package layouts.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import helpers.baloo
import helpers.primaryColor
import helpers.redColor

/**
 * Custom layout for a [OutlinedTextField]
 *
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction]
 * @param visualTransformation transforms the visual representation of the input [value]
 * @param text Text to display
 * @param value Modifier to be applied to the text
 * @param text Text to display
 * @param value Modifier to be applied to the text
 * @param onChange Text to display
 * @param trailingIcon size of the text, default 15[sp]
 * @param trailingOnClick Modifier to be applied to the text
 * @param modifier a [Modifier] for this text field
 */
@Composable
fun GliderTextField(
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    text: String,
    value: String,
    onChange: (String) -> Unit,
    trailingIcon: ImageVector,
    trailingOnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier,
        textStyle = TextStyle.Default.copy(fontFamily = baloo),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = primaryColor,
            focusedBorderColor = redColor,
            unfocusedBorderColor = primaryColor,
            focusedLabelColor = primaryColor,
            unfocusedLabelColor = primaryColor,
            cursorColor = primaryColor
        ),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        trailingIcon = {
            IconButton(
                onClick = trailingOnClick
            ) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    tint = primaryColor
                )
            }
        },
        label = { Text(text = text, fontFamily = baloo) },
        value = value,
        onValueChange = onChange
    )
}