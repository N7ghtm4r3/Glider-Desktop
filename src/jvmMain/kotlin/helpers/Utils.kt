package helpers

import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.ui.graphics.Color
import com.tecknobit.glider.helpers.GliderLauncher.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
 * the red color value
 */
val cBackgroundColor: Color = fromHexToColor("#1b1ba6")

/**
 * Method to mainScreen a [Color] from an hex [String]
 * @param hex: hex value to transform
 *
 * @return color as [Color]
 */
fun fromHexToColor(hex: String): Color {
    return Color(("ff" + hex.removePrefix("#").lowercase()).toLong(16))
}

/**
 * Method to show a snackbar from a view
 *
 * @param scope: scope manager
 * @param scaffoldState: state of the [Scaffold]
 * @param message: message to show
 * */
fun showSnack(scope: CoroutineScope, scaffoldState: ScaffoldState, message: String) {
    scope.launch {
        scaffoldState.snackbarHostState.showSnackbar(message)
    }
}