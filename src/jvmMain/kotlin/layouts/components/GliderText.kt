package layouts.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import helpers.backgroundColor

/**
 * Custom layout for a [Text]
 *
 * @param text Text to display
 * @param textColor color of the text
 * @param size size of the text, default 15[sp]
 * @param modifier Modifier to be applied to the text
 */
@Composable
fun GliderText(
    text: String,
    textColor: Color = backgroundColor,
    size: TextUnit = 15.sp,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = textColor,
        fontSize = size,
        modifier = modifier
    )
}