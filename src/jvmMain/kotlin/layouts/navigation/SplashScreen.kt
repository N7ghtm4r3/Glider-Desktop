package layouts.navigation

import Routes.connect
import Routes.create
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import helpers.*
import helpers.User.user
import kotlinx.coroutines.delay
import moe.tlaster.precompose.navigation.Navigator

/**
 * This is the layout for the splashscreen
 *
 * @author Tecknobit - N7ghtm4r3
 * **/
class SplashScreen {

    /**
     * Method to create the [SplashScreen] view
     * @param modifier: modifier for the layout
     */
    @Composable
    @Preview
    fun showSplashScreen(navigator: Navigator, modifier: Modifier = Modifier) {
        User().also { user = it }
        val blink = remember { Animatable(0f) }
        LaunchedEffect(key1 = true, block = {
            blink.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000)
            )
            delay(500)
            if (user.token != null)
                navigator.navigate(create.name)
            else
                navigator.navigate(connect.name)
        })
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.background(primaryColor).fillMaxSize()
        ) {
            Text(
                text = appName,
                color = backgroundColor.copy(blink.value),
                fontFamily = baloo,
                fontSize = 75.sp,
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "by Tecknobit",
                    color = backgroundColor,
                    fontFamily = baloo,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(25.dp)
                )
            }
        }
    }

}