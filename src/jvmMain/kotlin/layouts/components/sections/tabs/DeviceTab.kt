package layouts.components.sections.tabs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecknobit.glider.records.Device
import helpers.RequestManager
import helpers.primaryColor
import helpers.redColor
import helpers.showSnack

/**
 * This is the layout for the device tab where the user can manage own devices list connected to the session
 *
 * @author Tecknobit - N7ghtm4r3
 * @see RequestManager
 * @see Tab
 * **/
class DeviceTab : Tab() {

    /**
     * Method to create the [DeviceTab] view
     * @param device: the device to manage and to create the tab
     */
    @Composable
    fun createDeviceTab(device: Device?) {
        if (device != null) {
            val isBlacklisted = remember { mutableStateOf(device.isBlacklisted) }
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
                                        // TODO: REQUEST THEN
                                        showSnack(coroutineScope, scaffoldState, "Device blacklisted successfully")
                                        // AUTO REFRESHED
                                        isBlacklisted.value = true
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Block,
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
                                        // TODO: REQUEST THEN
                                        showSnack(coroutineScope, scaffoldState, "Device disconnected successfully")
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Logout,
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
                                        // TODO: REQUEST THEN
                                        showSnack(coroutineScope, scaffoldState, "Device unblacklisted successfully")
                                        // AUTO REFRESHED
                                        isBlacklisted.value = false
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Restore,
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
            showEmptyListLayout("No-any devices available")
    }

}