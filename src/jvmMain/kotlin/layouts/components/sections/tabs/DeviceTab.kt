package layouts.components.sections.tabs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecknobit.glider.helpers.GliderLauncher.Operation
import com.tecknobit.glider.helpers.GliderLauncher.Operation.DISCONNECT
import com.tecknobit.glider.helpers.GliderLauncher.Operation.MANAGE_DEVICE_AUTHORIZATION
import com.tecknobit.glider.records.Device
import com.tecknobit.glider.records.Device.DeviceKeys
import com.tecknobit.glider.records.Device.DeviceKeys.targetDevice
import helpers.RequestManager
import helpers.primaryColor
import helpers.redColor
import helpers.showSnack
import org.json.JSONObject

/**
 * This is the layout for the device tab where the user can manage own devices list connected to the session
 *
 * @author Tecknobit - N7ghtm4r3
 * @see RequestManager
 * @see Tab
 * **/
class DeviceTab : Tab() {

    /**
     * **isBlacklisted** -> whether the [Device] has been blacklisted
     */
    private lateinit var isBlacklisted: MutableState<Boolean>

    /**
     * Method to create the [DeviceTab] view
     * @param device: the device to manage and to create the tab
     */
    @Composable
    fun createDeviceTab(device: Device?) {
        if (device != null) {
            isBlacklisted = remember { mutableStateOf(device.isBlacklisted) }
            createTab {
                Row {
                    Column(Modifier.padding(start = 35.dp, top = 10.dp)) {
                        Row {
                            Text(
                                text = device.name,
                                color = Color.White,
                                fontSize = 45.sp
                            )
                        }
                        Row(Modifier.padding(top = 10.dp)) {
                            if (!isBlacklisted.value) {
                                OutlinedButton(
                                    shape = RoundedCornerShape(100.dp),
                                    border = BorderStroke(2.dp, Color.White),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.White
                                    ),
                                    onClick = {
                                        manageDevice(
                                            MANAGE_DEVICE_AUTHORIZATION, device,
                                            "Device blacklisted successfully", true
                                        )
                                    }
                                ) {
                                    Icon(
                                        imageVector = Default.Block,
                                        contentDescription = null,
                                        tint = primaryColor
                                    )
                                }
                                Spacer(Modifier.width(20.dp))
                                OutlinedButton(
                                    shape = RoundedCornerShape(100.dp),
                                    border = BorderStroke(2.dp, redColor),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = redColor
                                    ),
                                    onClick = {
                                        manageDevice(
                                            DISCONNECT, device, "Device disconnected successfully",
                                            false
                                        )
                                    }
                                ) {
                                    Icon(
                                        imageVector = Default.Logout,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            } else {
                                OutlinedButton(
                                    shape = RoundedCornerShape(100.dp),
                                    border = BorderStroke(2.dp, Color.White),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.White
                                    ),
                                    onClick = {
                                        manageDevice(
                                            MANAGE_DEVICE_AUTHORIZATION, device,
                                            "Device unblacklisted successfully", false
                                        )
                                    }
                                ) {
                                    Icon(
                                        imageVector = Default.Restore,
                                        contentDescription = null,
                                        tint = primaryColor
                                    )
                                }
                            }
                        }
                    }
                }
                Divider(Modifier.padding(top = 30.dp), thickness = 1.dp, color = Color.White)
                Row {
                    Column {
                        Text(
                            modifier = Modifier.padding(start = 35.dp, top = 10.dp),
                            text = "IP address",
                            color = Color.White,
                            fontSize = 30.sp
                        )
                        Row(Modifier.fillMaxWidth()) {
                            Text(
                                modifier = Modifier.padding(start = 45.dp, top = 10.dp),
                                text = device.ipAddress,
                                color = Color.White,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
                Divider(Modifier.padding(top = 30.dp), thickness = 1.dp, color = Color.White)
                Row {
                    Column {
                        Text(
                            modifier = Modifier.padding(start = 35.dp, top = 10.dp),
                            text = "Login date",
                            color = Color.White,
                            fontSize = 30.sp
                        )
                        Row(Modifier.fillMaxWidth()) {
                            Text(
                                modifier = Modifier.padding(start = 45.dp, top = 10.dp),
                                text = device.loginDate,
                                color = Color.White,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
                Divider(Modifier.padding(top = 30.dp), thickness = 1.dp, color = Color.White)
                Row {
                    Column {
                        Text(
                            modifier = Modifier.padding(start = 35.dp, top = 10.dp),
                            text = "Last activity",
                            color = Color.White,
                            fontSize = 30.sp
                        )
                        Row(Modifier.fillMaxWidth()) {
                            Text(
                                modifier = Modifier.padding(start = 45.dp, top = 10.dp),
                                text = device.lastActivity,
                                color = Color.White,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
                Divider(Modifier.padding(top = 30.dp), thickness = 1.dp, color = Color.White)
            }
        } else
            showEmptyListLayout("No devices available")
    }

    /**
     * Method to manage a device
     *
     * @param operation: the operation to execute -> [Operation.DISCONNECT], [Operation.MANAGE_DEVICE_AUTHORIZATION]
     * @param device: the device to manage
     * @param message: the message to show in the snackbar
     * */
    private fun manageDevice(operation: Operation, device: Device, message: String, blacklist: Boolean) {
        setRequestPayload(operation, null)
        payload!!.put(
            targetDevice.name, JSONObject()
                .put(DeviceKeys.name.name, device.name)
                .put(DeviceKeys.ipAddress.name, device.ipAddress)
        )
        socketManager!!.writeContent(payload)
        response = JSONObject(socketManager!!.readContent())
        if (successfulResponse()) {
            isBlacklisted.value = blacklist
            showSnack(coroutineScope, scaffoldState, message)
        } else
            showSnack(coroutineScope, scaffoldState, "Operation failed")
    }

}