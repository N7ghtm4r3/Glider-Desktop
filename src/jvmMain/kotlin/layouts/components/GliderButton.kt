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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import helpers.primaryColor

/**
 * Custom layout for a [Button]
 *
 * @param onClick Will be called when the user clicks the button
 * @param modifier Modifier to be applied to the button
 * @param text Text to display on the button
 */
@Composable
fun GliderButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = modifier.width(275.dp).height(55.dp).clip(RoundedCornerShape(10.dp)),
        colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor)
    ) {
        GliderText(text = text, textColor = Color.White, size = 20.sp)
    }
}