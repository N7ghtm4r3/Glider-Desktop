package layouts.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import helpers.primaryColor

/**
 * Custom layout for a [Button]
 *
 * @param width width of the button, default 275[Dp]
 * @param height height of the button, default 55[Dp]
 * @param onClick Will be called when the user clicks the button
 * @param modifier Modifier to be applied to the button
 * @param text Text to display on the button
 * @param textSize size of the text to display on the button
 */
@Composable
fun GliderButton(
    width: Dp = 275.dp,
    height: Dp = 55.dp,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    textSize: TextUnit = 20.sp
) {
    Button(
        onClick = onClick,
        modifier = modifier.width(width).height(height).clip(RoundedCornerShape(10.dp)),
        colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor)
    ) {
        GliderText(text = text, textColor = Color.White, size = textSize)
    }
}