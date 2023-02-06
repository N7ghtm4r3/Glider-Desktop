
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import helpers.appName
import helpers.backgroundColor
import helpers.primaryColor
import helpers.redColor
import layouts.navigation.SplashScreen

/**
 * Method to create the layout of **Glider** desktop app.
 * No any-params required
 */
@Composable
@Preview
fun App() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        MaterialTheme(
            colors = Colors(
                primary = primaryColor,
                primaryVariant = Color.White,
                secondary = backgroundColor,
                secondaryVariant = Color.White,
                background = backgroundColor,
                surface = backgroundColor,
                error = redColor,
                onPrimary = primaryColor,
                onSecondary = backgroundColor,
                onBackground = backgroundColor,
                onSurface = backgroundColor,
                onError = redColor,
                isLight = true
            )
        ) {
            val splashScreen = SplashScreen()
            splashScreen.showSplashScreen()
            splashScreen.openFirstPage()
        }
    }
}

/**
 * Method to start the of **Glider** desktop app.
 * No any-params required
 */
fun main() = application {
    Window(
        title = appName,
        icon = painterResource("logo.png"),
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
