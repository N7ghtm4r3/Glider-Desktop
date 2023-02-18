package layouts.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import helpers.*
import layouts.components.sections.Sidebar
import layouts.components.sections.lists.DevicesList
import layouts.components.sections.lists.PasswordsList
import layouts.components.sections.tabs.DeviceTab
import layouts.components.sections.tabs.PasswordTab

/**
 * This is the layout for the main screen of the application
 *
 * @author Tecknobit - N7ghtm4r3
 * @see RequestManager
 * **/
class Main : RequestManager() {

    companion object {

        /**
         * **showDevices** -> whether show the [PasswordsList] layout or the [DevicesList] layout
         */
        lateinit var showDevices: MutableState<Boolean>

    }

    /**
     * Method to create the [Main] view. No-any params required
     */
    @Composable
    fun createMainView() {
        showAlert = rememberSaveable { mutableStateOf(false) }
        showPopup = rememberSaveable { mutableStateOf(false) }
        showDevices = rememberSaveable { mutableStateOf(false) }
        val passwordsList: PasswordsList = remember { PasswordsList() }
        val passwordTab: PasswordTab = remember { PasswordTab() }
        val devicesList: DevicesList = remember { DevicesList() }
        val deviceTab: DeviceTab = remember { DeviceTab() }
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
                        if (!showDevices.value)
                            passwordsList.showPasswords()
                        else
                            devicesList.showDevices()
                    }
                    Column(
                        modifier = Modifier.weight(1f).fillMaxHeight().background(primaryColor)
                    ) {
                        if (!showDevices.value)
                            passwordTab.createPasswordTab(passwordsList.selectedItem())
                        else
                            deviceTab.createDeviceTab(devicesList.selectedItem())
                    }
                }
            }
        }
    }

}