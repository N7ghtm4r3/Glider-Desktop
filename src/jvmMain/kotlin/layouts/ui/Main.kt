package layouts.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import helpers.*
import layouts.components.sections.ListSection
import layouts.components.sections.PasswordTab
import layouts.components.sections.Sidebar

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
        showAlert = rememberSaveable { mutableStateOf(false) }
        showPopup = rememberSaveable { mutableStateOf(false) }
        val listSection: ListSection = remember { ListSection() }
        Scaffold(topBar = { TopAppBar(title = {}) }) {
            Box(
                Modifier.fillMaxSize()
            ) {
                if (showAlert.value)
                    createAlert()
                if (showPopup.value)
                    createPopup()
                Row {
                    Column(
                        modifier = Modifier.width(200.dp).fillMaxHeight().background(primaryColor)
                    ) {
                        Sidebar().sidebar()
                    }
                    Column(
                        modifier = Modifier.weight(1f).fillMaxHeight().background(backgroundColor)
                    ) {
                        listSection.listSection()
                    }
                    Column(
                        modifier = Modifier.weight(1f).fillMaxHeight().background(primaryColor)
                    ) {
                        PasswordTab().createTab(listSection.selectedPassword())
                    }
                }
            }
        }
    }

}