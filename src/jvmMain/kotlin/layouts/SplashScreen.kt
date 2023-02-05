package layouts

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import helpers.appName
import helpers.backgroundColor
import helpers.primaryColor

class SplashScreen {

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
            )
        }
    }

}