package layouts.components.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecknobit.glider.helpers.GliderLauncher.Operation
import com.tecknobit.glider.helpers.GliderLauncher.Operation.DELETE_SESSION
import com.tecknobit.glider.helpers.GliderLauncher.Operation.DISCONNECT
import helpers.*
import helpers.User.Companion.user
import kotlinx.coroutines.CoroutineScope
import layouts.components.forms.CreateForm
import layouts.components.forms.InsertForm
import layouts.ui.Main.Companion.resetSession
import layouts.ui.Main.Companion.showDevices
import org.json.JSONObject

/**
 * This is the layout for the sidebar where the user can navigate in the app
 *
 * @author Tecknobit - N7ghtm4r3
 * @see RequestManager
 * **/
class Sidebar : RequestManager() {

    companion object {

        /**
         * **coroutineScope** -> the coroutine scope to manage the coroutines of the [scaffoldState]
         */
        lateinit var coroutineScope: CoroutineScope

        /**
         * **scaffoldState** -> the scaffold state for the scaffold of the page
         */
        lateinit var scaffoldState: ScaffoldState

    }

    /**
     * Method to create the [Sidebar] view. No-any params required
     */
    @Composable
    fun sidebar() {
        coroutineScope = rememberCoroutineScope()
        scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = primaryColor,
            contentColor = primaryColor,
            snackbarHost = {
                SnackbarHost(it) { data ->
                    Snackbar(
                        backgroundColor = backgroundColor,
                        contentColor = primaryColor,
                        snackbarData = data
                    )
                }
            }
        ) {
            Column {
                val menuItems = mapOf(
                    Pair("Create", Pair(Default.Add) {
                        if (user.isPasswordManager())
                            CreateForm().createPassword()
                        else
                            showNotAuthorizedSnack()
                    }),
                    Pair("Insert", Pair(Default.Create) {
                        if (user.isPasswordManager())
                            InsertForm().insertPassword()
                        else
                            showNotAuthorizedSnack()
                    }),
                    Pair("Passwords", Pair(Default.ViewList) { showDevices.value = false }),
                    Pair("Account", Pair(Default.ManageAccounts) { showDevices.value = true })
                )
                Divider(thickness = 1.dp, color = Color.White)
                menuItems.forEach { item ->
                    menuItem(item)
                }
                Row {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val bottomItems = mapOf(
                            Pair("Logout", Pair(Default.Logout) { manageSession(DISCONNECT) }),
                            Pair("Delete", Pair(Default.NoAccounts) {
                                if (user.isAdmin()) {
                                    fillAlertContent(
                                        title = "Account deletion",
                                        text = {
                                            Text(
                                                text = "This will delete permanently all the data of the account and all the " +
                                                        "passwords will be unrecoverable, confirm?",
                                                color = Black
                                            )
                                        },
                                        confirmAction = { manageSession(DELETE_SESSION) },
                                    )
                                    showAlert()
                                } else
                                    showNotAuthorizedSnack()
                            })
                        )
                        Divider(thickness = 1.dp, color = Color.White)
                        bottomItems.forEach { item ->
                            bottomMenuItem(item)
                        }
                        Spacer(Modifier.height(20.dp))
                        Text(
                            text = "Glider Desktop",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "v. 1.0.1",
                            color = Color.White,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }

    /**
     * Method to create a menu item for the sidebar
     *
     * @param item: the menu item details
     */
    @Composable
    private fun menuItem(item: Map.Entry<String, Pair<ImageVector, () -> Unit>>) {
        Column(
            modifier = Modifier.height(50.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            bottomMenuItem(item)
        }
    }

    /**
     * Method to create a menu item and align it at the bottom of the page for the sidebar
     *
     * @param item: the menu item details
     */
    @Composable
    private fun bottomMenuItem(item: Map.Entry<String, Pair<ImageVector, () -> Unit>>) {
        IconButton(onClick = item.value.second) {
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
                        imageVector = item.value.first,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
        Divider(thickness = 1.dp, color = Color.White)
    }

    /**
     * Method to show the `not authorized` snackbar.
     * No-any params required
     */
    private fun showNotAuthorizedSnack() {
        showSnack(coroutineScope, scaffoldState, "Not authorized")
    }

    /**
     * Method to manage the user session
     *
     * @param operation: the operation to execute -> [Operation.DISCONNECT], [Operation.DELETE_SESSION]
     */
    private fun manageSession(operation: Operation) {
        setRequestPayload(operation, null)
        socketManager!!.writeContent(payload)
        response = JSONObject(socketManager!!.readContent())
        if (successfulResponse()) {
            if (operation == DELETE_SESSION)
                showAlert.value = false
            resetSession(null)
        } else
            showSnack(coroutineScope, scaffoldState, "Operation failed")
    }

}