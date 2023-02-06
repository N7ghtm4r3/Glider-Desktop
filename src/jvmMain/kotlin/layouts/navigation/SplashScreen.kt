package layouts.navigation

import androidx.compose.animation.core.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import helpers.User.user
import helpers.appName
import helpers.backgroundColor
import helpers.baloo
import helpers.primaryColor
import layouts.ui.Create

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
    fun showSplashScreen(modifier: Modifier = Modifier) {
        val flashAnimation by rememberInfiniteTransition().animateFloat(
            initialValue = 1f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                tween(1000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.background(primaryColor).fillMaxSize()
        ) {
            Text(
                text = appName,
                color = backgroundColor.copy(flashAnimation),
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

    /**
     * Method to navigate to the first page of the app. No-any params required
     */
    fun openFirstPage() {
        helpers.User().also { user = it }
        if (user.token == null)
            Connect()
        else
            Create()
    }

}