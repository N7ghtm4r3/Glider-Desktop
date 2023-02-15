package layouts.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import helpers.RequestManager
import helpers.backgroundColor
import helpers.primaryColor
import layouts.components.ListSection
import layouts.components.Sidebar

/**
 * This is the layout for the main screen of the application
 *
 * @author Tecknobit - N7ghtm4r3
 * @see RequestManager
 * **/
class Main : RequestManager() {

    /**
     * Method to create the [Main] view. No-any params required
     */
    @Composable
    fun createMainView() {
        Scaffold(topBar = { TopAppBar(title = {}) }) {
            Box(
                Modifier.fillMaxSize()
            ) {
                Row {
                    Column(
                        modifier = Modifier.width(200.dp).fillMaxHeight().background(primaryColor)
                    ) {
                        Sidebar().sidebar()
                    }
                    Column(
                        modifier = Modifier.weight(1f).fillMaxHeight().background(backgroundColor)
                    ) {
                        ListSection().listSection()
                    }
                    Column(
                        modifier = Modifier.weight(1f).fillMaxHeight().background(Color.Black)
                    ) {

                    }
                }
            }
        }
    }

}