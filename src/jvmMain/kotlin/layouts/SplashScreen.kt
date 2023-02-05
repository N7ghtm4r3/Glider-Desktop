package layouts

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import helpers.appName
import helpers.backgroundColor
import helpers.baloo
import helpers.primaryColor

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
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.background(primaryColor).fillMaxSize()
        ) {
            Text(
                text = appName,
                color = backgroundColor,
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