package helpers

import androidx.compose.ui.graphics.Color
import com.tecknobit.glider.helpers.GliderLauncher.*

const val appName: String = "Glider"
val primaryColor: Color = fromHexToColor(COLOR_PRIMARY_HEX)
val backgroundColor: Color = fromHexToColor(BACKGROUND_COLOR_HEX)
val redColor: Color = fromHexToColor(COLOR_RED_HEX)

private fun fromHexToColor(hex: String): Color {
    return Color(("ff" + hex.removePrefix("#").lowercase()).toLong(16))
}