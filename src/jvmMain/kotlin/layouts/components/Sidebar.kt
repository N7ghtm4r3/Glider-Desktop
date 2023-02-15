package layouts.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * This is the layout for the sidebar where the user can navigate in the app
 *
 * @author Tecknobit - N7ghtm4r3
 * **/
class Sidebar {

    /**
     * Method to create the [Sidebar] view. No-any params required
     */
    @Composable
    fun sidebar() {
        Column {
            val menuItems = mapOf(
                Pair("Create", Default.Add), Pair("Insert", Default.Create),
                Pair("Sessions", Default.ManageAccounts), Pair("Devices", Default.Devices)
            )
            menuItems.forEach { item ->
                menuItem({}, item)
            }
            Row {
                Column(
                    modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val bottomItems = mapOf(Pair("Logout", Default.Logout), Pair("Delete", Default.NoAccounts))
                    Divider(thickness = 1.dp, color = Color.White)
                    bottomItems.forEach { item ->
                        bottomMenuItem({}, item)
                    }
                    Spacer(Modifier.height(20.dp))
                    Text(
                        text = "Glider Desktop",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "v. " + "1.0.0",
                        color = Color.White,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }

    /**
     * Method to create a menu item for the sidebar
     *
     * @param onClick: the onClick content to execute
     * @param item: the menu item details
     */
    @Composable
    private fun menuItem(onClick: () -> Unit, item: Map.Entry<String, ImageVector>) {
        Column(
            modifier = Modifier.height(50.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            bottomMenuItem(onClick, item)
        }
    }

    /**
     * Method to create a menu item and align it at the bottom of the page for the sidebar
     *
     * @param onClick: the onClick content to execute
     * @param item: the menu item details
     */
    @Composable
    private fun bottomMenuItem(onClick: () -> Unit, item: Map.Entry<String, ImageVector>) {
        IconButton(onClick = onClick) {
            Row(horizontalArrangement = Arrangement.Center) {
                Row {
                    Text(
                        text = item.key,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
                Spacer(Modifier.width(10.dp))
                Row {
                    Icon(
                        imageVector = item.value,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
        Divider(thickness = 1.dp, color = Color.White)
    }

}