package helpers

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import com.tecknobit.glider.helpers.GliderLauncher.*

/**
 * app name constant
 */
const val appName: String = "Glider"

/**
 * the primary color value
 */
val primaryColor: Color = fromHexToColor(COLOR_PRIMARY_HEX)

/**
 * the background color value
 */
val backgroundColor: Color = fromHexToColor(BACKGROUND_COLOR_HEX)

/**
 * the red color value
 */
val redColor: Color = fromHexToColor(COLOR_RED_HEX)

/**
 * {@code baloo} the font family for the app
 */
val baloo = FontFamily(Font(resource = "baloo.ttf"))

/**
 * Method to create a [Color] from an hex [String]
 * @param hex: hex value to transform
 *
 * @return color as [Color]
 */
fun fromHexToColor(hex: String): Color {
    return Color(("ff" + hex.removePrefix("#").lowercase()).toLong(16))
}