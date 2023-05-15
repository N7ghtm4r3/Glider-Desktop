package layouts.components.sections.tabs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecknobit.glider.helpers.GliderLauncher.Operation
import com.tecknobit.glider.helpers.GliderLauncher.Operation.*
import com.tecknobit.glider.records.Password
import com.tecknobit.glider.records.Password.PasswordKeys
import com.tecknobit.glider.records.Password.Status
import helpers.*
import layouts.components.GliderButton
import layouts.components.GliderTextField
import org.json.JSONObject
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection

/**
 * This is the layout for the password tab where the user can manage own [Password]
 *
 * @author Tecknobit - N7ghtm4r3
 * @see RequestManager
 * @see Tab
 * **/
class PasswordTab : Tab() {

    /**
     * **hidePassword** -> whether the [Password] is hidden
     */
    private var hidePassword: Boolean = true

    /**
     * **passwordStatus** -> the [Password]'s status
     */
    private lateinit var passwordStatus: Status

    /**
     * Method to create the [PasswordTab] view
     * @param password: the password to manage and to create the tab
     */
    @Composable
    fun createPasswordTab(password: Password?) {
        if (password != null) {
            passwordStatus = password.status
            createTab {
                Row {
                    Column(Modifier.padding(start = 35.dp, top = 10.dp)) {
                        Row {
                            Text(
                                text = password.tail,
                                color = Color.White,
                                fontSize = 45.sp
                            )
                        }
                        Row(Modifier.padding(top = 10.dp)) {
                            OutlinedButton(
                                shape = RoundedCornerShape(100.dp),
                                border = BorderStroke(2.dp, redColor),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = redColor
                                ),
                                onClick = {
                                    managePassword(
                                        DELETE_PASSWORD, password.tail,
                                        if (passwordStatus == Status.DELETED)
                                            "Password permanently deleted successfully"
                                        else
                                            "Password deleted successfully"
                                    )
                                }
                            ) {
                                Icon(
                                    imageVector = Default.Delete,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                            Spacer(Modifier.width(20.dp))
                            OutlinedButton(
                                shape = RoundedCornerShape(100.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.White
                                ),
                                onClick = {
                                    if (passwordStatus == Status.DELETED)
                                        managePassword(
                                            RECOVER_PASSWORD,
                                            password.tail,
                                            "Password recovered successfully"
                                        )
                                    else {
                                        val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
                                        clipboard.setContents(StringSelection(password.password), null)
                                        showSnack(coroutineScope, scaffoldState, "Password copied successfully")
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (passwordStatus == Status.ACTIVE) Default.ContentCopy else Default.Restore,
                                    contentDescription = null,
                                    tint = primaryColor
                                )
                            }
                        }
                    }
                }
                Divider(Modifier.padding(top = 30.dp), thickness = 1.dp, color = Color.White)
                Row {
                    Column {
                        Text(
                            modifier = Modifier.padding(start = 35.dp, top = 10.dp),
                            text = "Password",
                            color = Color.White,
                            fontSize = 30.sp
                        )
                        Row(Modifier.fillMaxWidth()) {
                            TextButton(
                                onClick = { hidePassword = !hidePassword }
                            ) {
                                Text(
                                    modifier = Modifier.padding(start = 45.dp, top = 10.dp),
                                    text = if (!hidePassword) password.password else hidePassword(password),
                                    color = Color.White,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
                Divider(Modifier.padding(top = 30.dp), thickness = 1.dp, color = Color.White)
                Row {
                    Column {
                        Column(
                            modifier = Modifier.padding(start = 35.dp, top = 10.dp),
                        ) {
                            Row {
                                Text(
                                    text = "Scopes",
                                    color = Color.White,
                                    fontSize = 30.sp
                                )
                            }
                            Modifier.padding(top = 10.dp)
                            Row {
                                if (passwordStatus == Status.ACTIVE) {
                                    Row {
                                        OutlinedButton(
                                            shape = RoundedCornerShape(100),
                                            border = BorderStroke(2.dp, Color.White),
                                            colors = ButtonDefaults.buttonColors(
                                                backgroundColor = Color.White
                                            ),
                                            onClick = {
                                                createPopupScope(
                                                    "Add a new scope", "New scope",
                                                    "The new scope to add", "Add", password.tail,
                                                    message = "Scope added successfully"
                                                )
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Default.Add,
                                                contentDescription = null,
                                                tint = primaryColor
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        val scopes = password.scopesSorted.toList()
                        if (scopes.isNotEmpty()) {
                            // TODO: MANAGE WHEN IS CREATED WHEN SCROLLING COLUMN
                            LazyVerticalGrid(
                                modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                                columns = GridCells.Adaptive(140.dp),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(scopes) { scope ->
                                    TextButton(
                                        onClick = {
                                            if (passwordStatus == Status.ACTIVE) {
                                                createPopupScope(
                                                    "Edit an old scope", "Edit scope",
                                                    "The scope to edit", "Edit", password.tail,
                                                    scope, "Scope edited successfully"
                                                )
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color.White
                                        ),
                                        shape = RoundedCornerShape(100)
                                    ) {
                                        Row {
                                            Column(Modifier.weight(1f).fillMaxHeight().padding(start = 5.dp)) {
                                                Text(
                                                    text = scope,
                                                    maxLines = 1
                                                )
                                            }
                                            if (passwordStatus == Status.ACTIVE) {
                                                Column(
                                                    modifier = Modifier.weight(1f).fillMaxHeight(),
                                                    horizontalAlignment = Alignment.End,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Icon(
                                                        modifier = Modifier.size(20.dp).clickable {
                                                            manageScope(
                                                                REMOVE_SCOPE, password.tail, scope,
                                                                message = "Scope deleted successfully"
                                                            )
                                                        },
                                                        imageVector = Default.Clear,
                                                        contentDescription = null
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "No scopes attached",
                                    color = Color.White,
                                    fontSize = 18.sp
                                )
                            }
                        }
                        Divider(Modifier.padding(top = 30.dp), thickness = 1.dp, color = Color.White)
                    }
                }
            }
        } else
            showEmptyListLayout("No passwords available")
    }

    /**
     * Method to create a popup to manage the [Operation.ADD_SCOPE] and [Operation.EDIT_SCOPE] operations
     *
     * @param title: the title of the popup
     * @param hint: the hint message to show
     * @param snackHint: the hint message to show in the snackbar
     * @param btnText: the text of the button
     * @param tail: the tail of the password to manage
     * @param oldScope: the old scope to remove if filled
     * @param message: the message to show in the snackbar
     * */
    private fun createPopupScope(
        title: String, hint: String, snackHint: String, btnText: String, tail: String,
        oldScope: String? = null, message: String
    ) {
        fillPopupContent {
            var wScope by rememberSaveable { mutableStateOf("") }
            val isInError = remember { mutableStateOf(false) }
            Text(
                modifier = Modifier.padding(start = 25.dp, top = 35.dp),
                text = title,
                fontSize = 18.sp,
                color = primaryColor
            )
            Column(
                modifier = Modifier.fillMaxSize().padding(top = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                GliderTextField(
                    modifier = Modifier.width(250.dp),
                    isError = isInError.value,
                    text = hint,
                    value = wScope,
                    onChange = {
                        wScope = it.replace(" ", "")
                        isInError.value = wScope.isEmpty()
                    },
                    leadingIcon = Default.Info,
                    leadingOnClick = { showPopupSnack(snackHint) },
                    trailingIcon = Default.Clear,
                    trailingOnClick = {
                        wScope = ""
                    }
                )
                Spacer(Modifier.height(15.dp))
                GliderButton(
                    modifier = Modifier.width(250.dp),
                    onClick = {
                        if (wScope.isNotEmpty()) {
                            manageScope(
                                if (oldScope == null) ADD_SCOPE else EDIT_SCOPE,
                                tail,
                                wScope,
                                oldScope,
                                message
                            )
                            dismissPopup()
                        } else {
                            isInError.value = true
                            showPopupSnack("The scope is required")
                        }
                    },
                    text = btnText,
                    textSize = 16.5.sp
                )
            }
        }
        showPopup(Modifier.width(300.dp).height(250.dp))
    }

    /**
     * Method to manage a scope
     *
     * @param operation: the operation to execute -> [Operation.ADD_SCOPE], [Operation.EDIT_SCOPE], [Operation.REMOVE_SCOPE]
     * @param tail: the tail of the password to manage
     * @param scope: the scope to manage
     * @param oldScope: the old scope to remove if filled
     * @param message: the message to show in the snackbar
     * */
    private fun manageScope(
        operation: Operation,
        tail: String,
        scope: String,
        oldScope: String? = null,
        message: String
    ) {
        setRequestPayload(operation, null)
        payload!!.put(PasswordKeys.tail.name, tail).put(PasswordKeys.scope.name, scope)
        if (oldScope != null)
            payload!!.put(PasswordKeys.oldScope.name, oldScope)
        socketManager!!.writeContent(payload)
        response = JSONObject(socketManager!!.readContent())
        if (successfulResponse())
            showSnack(coroutineScope, scaffoldState, message)
        else
            showSnack(coroutineScope, scaffoldState, "Operation failed")
    }

    /**
     * Method to manage a password
     *
     * @param operation: the operation to execute -> [Operation.DELETE_PASSWORD], [Operation.RECOVER_PASSWORD]
     * @param tail: the tail of the password to manage
     * @param message: the message to show in the snackbar
     * */
    private fun managePassword(operation: Operation, tail: String, message: String) {
        setRequestPayload(operation, null)
        payload!!.put(PasswordKeys.tail.name, tail)
        socketManager!!.writeContent(payload)
        response = JSONObject(socketManager!!.readContent())
        if (successfulResponse())
            showSnack(coroutineScope, scaffoldState, message)
        else
            showSnack(coroutineScope, scaffoldState, "Operation failed")
    }

    /**
     * Method to hide a password
     */
    fun hidePassword() {
        hidePassword = true
    }

    /**
     * Method to hide a password
     *
     * @param password: the password to hide
     * @return password hidden as [String]
     */
    private fun hidePassword(password: Password): String {
        var hiddenPassword = ""
        repeat(password.password.length) {
            hiddenPassword += "*"
        }
        return hiddenPassword
    }

}