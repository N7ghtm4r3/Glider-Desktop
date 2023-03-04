package layouts.ui

import Routes.connect
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
import com.tecknobit.glider.helpers.DatabaseManager.Table
import com.tecknobit.glider.helpers.GliderLauncher.Operation.REFRESH_DATA
import com.tecknobit.glider.records.Device
import com.tecknobit.glider.records.Password
import helpers.*
import helpers.User.Companion.devices
import helpers.User.Companion.passwords
import helpers.User.Companion.user
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import layouts.components.sections.Sidebar
import layouts.components.sections.lists.DevicesList
import layouts.components.sections.lists.PasswordsList
import layouts.ui.navigation.Connect
import layouts.ui.navigation.Connect.Companion.snackMessage
import navigator
import org.json.JSONArray
import org.json.JSONObject
import java.net.ConnectException
import java.net.SocketException

/**
 * This is the layout for the main screen of the application
 *
 * @author Tecknobit - N7ghtm4r3
 * @see RequestManager
 * **/
class Main : RequestManager() {

    companion object {

        /**
         * **continueRefreshData** -> whether continue to refresh the user session data
         */
        @Volatile
        var continueRefreshData = true

        /**
         * **showDevices** -> whether show the [PasswordsList] layout or the [DevicesList] layout
         */
        lateinit var showDevices: MutableState<Boolean>

        /**
         * Method to clear the user session and navigate to the [Connect] page
         *
         * @param message: the message to show in the snackbar
         */
        fun resetSession(message: String?) {
            continueRefreshData = false
            user.clearUserSession()
            snackMessage = message
            navigator.navigate(connect.name)
        }

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
        val devicesList: DevicesList = remember { DevicesList() }
        refreshUserData()
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
                }
            }
        }
    }

    /**
     * Method to refresh the **Glider** data. No-any params required
     */
    private fun refreshUserData() {
        continueRefreshData = true
        CoroutineScope(Dispatchers.Default).launch {
            var currentPasswords = JSONArray()
            var currentDevices = JSONArray()
            while (true) {
                if (continueRefreshData) {
                    try {
                        this@Main.setRequestPayload(REFRESH_DATA, null)
                        socketManager!!.writeContent(payload)
                        try {
                            response = JSONObject(socketManager!!.readContent())
                            if (successfulResponse()) {
                                val jPasswords = response.getJSONArray(Table.passwords.name)
                                val sPasswords = jPasswords.toString()
                                if (currentPasswords.toString() != sPasswords) {
                                    passwords.clear()
                                    for (j in 0 until jPasswords.length())
                                        passwords.add(Password(jPasswords.getJSONObject(j)))
                                    currentPasswords = JSONArray(sPasswords)
                                }
                                val jDevices = response.getJSONArray(Table.devices.name)
                                val sDevices = jDevices.toString()
                                if (currentDevices.toString() != sDevices) {
                                    devices.clear()
                                    for (j in 0 until jDevices.length())
                                        devices.add(Device(jDevices.getJSONObject(j)))
                                    currentDevices = JSONArray(sDevices)
                                }
                            } else if (genericResponse())
                                resetSession("This device has been disconnected")
                            else
                                resetSession("The session has been deleted")
                        } catch (_: IllegalArgumentException) {
                        } catch (s: SocketException) {
                            resetSession("The session has been deleted")
                        } finally {
                            withContext(Dispatchers.IO) {
                                Thread.sleep(5000)
                            }
                        }
                    } catch (e: ConnectException) {
                        resetSession("The session has been deleted")
                    }
                } else
                    break
            }
        }
    }

}