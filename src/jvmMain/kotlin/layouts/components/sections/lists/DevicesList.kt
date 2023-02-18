package layouts.components.sections.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecknobit.apimanager.formatters.TimeFormatter.getStringDate
import com.tecknobit.glider.records.Device
import com.tecknobit.glider.records.Device.Type.DESKTOP
import helpers.User.Companion.user
import helpers.backgroundColor
import helpers.primaryColor
import helpers.redColor
import java.lang.System.currentTimeMillis
import java.net.InetAddress

/**
 * This is the layout for the list section where there is the list of the devices connected to the session
 *
 * @author Tecknobit - N7ghtm4r3
 * @see List
 * **/
class DevicesList : List() {

    /**
     * Method to create the [DevicesList] view. No-any params required
     */
    @Composable
    fun showDevices() {
        // TODO: USE THE CORRECT LIST
        itemsList = mutableListOf(
            Device(
                InetAddress.getLocalHost().hostName, "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), DESKTOP
            ),
            Device(
                "gagagagaga", "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.MOBILE
            ),
            Device(
                "gagagagaga", "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.MOBILE
            ),
            Device(
                "gagagagaga", "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.MOBILE
            ),
            Device(
                "gagagagaga", "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.DESKTOP
            ),
            Device(
                "gagagagaga", "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.MOBILE
            ),
            Device(
                InetAddress.getLocalHost().hostName, "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.DESKTOP
            ),
            Device(
                "gagagagaga", "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.MOBILE
            ),
            Device(
                "gagagagaga", "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.DESKTOP
            ),
            Device(
                "gagagagaga", "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.MOBILE
            ),
            Device(
                "gagagagaga", "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.MOBILE
            ),
            Device(
                "gagagagaga", "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.MOBILE
            ),
            Device(
                InetAddress.getLocalHost().hostName, "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.DESKTOP
            ),
            Device(
                "gagagagaga", "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.MOBILE
            ),
            Device(
                "gagagagaga", "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.MOBILE
            ),
            Device(
                "gagagagaga", "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.DESKTOP
            ),
            Device(
                "gagagagaga", "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.MOBILE
            ),
            Device(
                "gagagagaga", "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.MOBILE
            ),
            Device(
                InetAddress.getLocalHost().hostName, "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.DESKTOP
            ),
            Device(
                "gagagagaga", "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.MOBILE
            ),
            Device(
                "gagagagaga", "192.168.1.10", getStringDate(currentTimeMillis()),
                getStringDate(currentTimeMillis()), Device.Type.MOBILE
            ),
        )
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 46.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
                backgroundColor = Color.White,
                shape = RoundedCornerShape(10.dp),
                elevation = 5.dp
            ) {
                Column {
                    createCardHeader("Server details")
                    createCardItem(Modifier.padding(start = 25.dp, top = 10.dp), "Address", user.hostAddress)
                    createCardItem(key = "Port", value = user.hostPort)
                    createCardHeader("Security")
                    createCardItem(Modifier.padding(start = 25.dp, top = 10.dp), "Single use", user.isSingleUseMode)
                    createCardItem(key = "QR Code login", value = user.isQRCodeLoginEnabled)
                    createCardItem(
                        Modifier.padding(start = 25.dp, bottom = 15.dp), key = "Only in localhost",
                        value = user.runInLocalhost()
                    )
                }
            }
            if (!user.isSingleUseMode) {
                loadList {
                    items(itemsList) { device ->
                        device as Device
                        Card(
                            modifier = Modifier.fillMaxWidth().height(65.dp).clickable {
                                selectedItem.value = device
                            },
                            backgroundColor = Color.White,
                            shape = RoundedCornerShape(10.dp),
                            elevation = 5.dp
                        ) {
                            Row {
                                Column(
                                    modifier = Modifier.weight(1f).fillMaxHeight(),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        modifier = Modifier.padding(start = 10.dp),
                                        text = device.name,
                                        color = primaryColor,
                                        fontSize = 20.sp
                                    )
                                }
                                Column(
                                    modifier = Modifier.weight(1f).fillMaxHeight(),
                                    horizontalAlignment = Alignment.End,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Box(
                                        modifier = Modifier.background(primaryColor).fillMaxHeight().width(100.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = if (device.type == DESKTOP) Default.Computer else Default.PhoneAndroid,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else
                selectedItem = remember { mutableStateOf(null) }
        }
    }

    /**
     * Method to create a card header
     * @param header: header value to create
     * */
    @Composable
    private fun createCardHeader(header: String) {
        Row(
            Modifier.padding(start = 20.dp, top = if (header.contains("Server")) 20.dp else 5.dp)
        ) {
            Text(
                text = header,
                color = primaryColor,
                fontSize = 18.sp
            )
        }
        Divider(Modifier.padding(top = 5.dp, end = 20.dp), color = backgroundColor, thickness = 2.dp)
    }

    /**
     * Method to create a card item
     *
     * @param modifier: modifier of the layout for the card item
     * @param key: key of the card item to create
     * @param value: value of the card item to create
     * */
    @Composable
    private fun createCardItem(modifier: Modifier = Modifier.padding(start = 25.dp), key: String, value: Any) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$key:",
                color = redColor,
                fontSize = 18.sp
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = if (value is Boolean) value.toString().uppercase() else value.toString(),
                color = primaryColor,
                fontSize = 16.sp
            )
        }
    }

}