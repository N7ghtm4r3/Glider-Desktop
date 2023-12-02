
import Routes.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import helpers.appName
import helpers.backgroundColor
import helpers.primaryColor
import helpers.redColor
import layouts.ui.Main
import layouts.ui.navigation.Connect
import layouts.ui.navigation.SplashScreen
import moe.tlaster.precompose.PreComposeWindow
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

/**
 * **navigator** -> the navigator instance is useful to manage the navigation between the screens of the application
 */
lateinit var navigator: Navigator

/**
 * List of available routes of the app
 */
enum class Routes {

    /**
     * **splashScreen** -> the first screen of the app where are loaded the user data for the session
     * and managed the first page to open
     */
    splashScreen,

    /**
     * **connect** -> the screen where the user can connect with own session to use in the app
     */
    connect,

    /**
     * **mainScreen** -> the screen where the user can manage the passwords, create or insert a new password
     */
    mainScreen

}

/**
 * Method to mainScreen the layout of **Glider** desktop app.
 * No any-params required
 */
@Composable
@Preview
fun App() {
    navigator = rememberNavigator()
    MaterialTheme(
        typography = Typography(defaultFontFamily = FontFamily(Font(resource = "baloo.ttf"))),
        colors = Colors(
            primary = primaryColor,
            primaryVariant = primaryColor,
            secondary = backgroundColor,
            secondaryVariant = primaryColor,
            background = backgroundColor,
            surface = Transparent,
            error = redColor,
            onPrimary = backgroundColor,
            onSecondary = primaryColor,
            onBackground = backgroundColor,
            onSurface = primaryColor,
            onError = redColor,
            isLight = true
        )
    ) {
        NavHost(
            navigator = navigator,
            navTransition = NavTransition(),
            initialRoute = splashScreen.name,
        ) {
            scene(
                route = splashScreen.name,
                navTransition = NavTransition(),
            ) {
                SplashScreen().showSplashScreen()
            }
            scene(
                route = connect.name,
                navTransition = NavTransition(),
            ) {
                Connect().connect()
            }
            scene(
                route = mainScreen.name,
                navTransition = NavTransition(),
            ) {
                Main().createMainView()
            }
        }
    }
}

/**
 * Method to start the of **Glider** desktop app.
 * No any-params required
 */
fun main() = application {
    PreComposeWindow(
        title = appName,
        icon = painterResource("logo.png"),
        state = WindowState(placement = WindowPlacement.Maximized),
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
