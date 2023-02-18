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
import com.tecknobit.glider.records.Session
import helpers.*
import layouts.components.sections.Sidebar
import layouts.components.sections.lists.DevicesList
import layouts.components.sections.lists.PasswordsList
import layouts.components.sections.tabs.PasswordTab

/**
 * This is the layout for the main screen of the application
 *
 * @author Tecknobit - N7ghtm4r3
 * @see RequestManager
 * **/
class Main : RequestManager() {

    companion object {

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
        //val devicesList: DevicesList = remember { DevicesList() }
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
                        else {
                            // TODO: PASS THE CORRECT SESSION INSTANCE
                            devicesList.showDevices(
                                Session(
                                    "gagagv34y293u646u260t921ìut",
                                    "gaio2vnìvjodaegvqg?=",
                                    "gase'+qj2w3e409yhj0ì'9h+gyt32gei+nNB+GHQEBW+G==",
                                    "GAGVAGAWGA",
                                    "LOCALHOST",
                                    22,
                                    false,
                                    false,
                                    false
                                )
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1f).fillMaxHeight().background(primaryColor)
                    ) {
                        passwordTab.createTab(passwordsList.selectedItem())
                    }
                }
            }
        }
    }

}