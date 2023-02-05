
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import layouts.SplashScreen

@Composable
@Preview
fun App() {
    val splash: SplashScreen = remember { SplashScreen() }
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
                surface = Color.Unspecified,
                error = redColor,
                onPrimary = Color.Unspecified,
                onSecondary = Color.Unspecified,
                onBackground = Color.Unspecified,
                onSurface = Color.Unspecified,
                onError = redColor,
                isLight = true
            )
        ) {
            splash.showSplashScreen()
        }
    }
}

fun main() = application {
    Window(
        title = appName,
        icon = painterResource("logo.png"),
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
