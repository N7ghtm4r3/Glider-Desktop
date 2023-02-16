package layouts.components

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecknobit.glider.records.Password
import com.tecknobit.glider.records.Password.Status
import com.tecknobit.glider.records.Password.Status.ACTIVE
import helpers.*
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection

/**
 * This is the layout for the password tab where the user can manage own [Password]
 *
 * @author Tecknobit - N7ghtm4r3
 * @see RequestManager
 * **/
class PasswordTab : RequestManager() {

    /**
     * **scaffoldState** -> the scaffold state for the scaffold of the page
     */
    private lateinit var scaffoldState: ScaffoldState

    /**
     * **hidePassword** -> whether the [Password] is hidden
     */
    private var hidePassword = mutableStateOf(true)

    /**
     * **passwordStatus** -> the [Password]'s status
     */
    private lateinit var passwordStatus: Status

    /**
     * Method to create the [PasswordTab] view
     * @param password: the password to manage and to create the tab
     */
    @Composable
    fun createTab(password: Password) {
        val coroutineScope = rememberCoroutineScope()
        scaffoldState = rememberScaffoldState()
        passwordStatus = password.status
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
            Divider(thickness = 1.dp, color = White)
            Column(modifier = Modifier.fillMaxSize().padding(top = 30.dp)) {
                Row {
                    Column(Modifier.padding(start = 35.dp, top = 10.dp)) {
                        Row {
                            Text(
                                text = password.tail,
                                color = White,
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
                                    if (passwordStatus == Status.DELETED) {
                                        // TODO: REQUEST THEN
                                        showSnack(
                                            coroutineScope,
                                            scaffoldState,
                                            "Password permanently deleted successfully"
                                        )
                                    } else {
                                        // TODO: REQUEST THEN
                                        showSnack(coroutineScope, scaffoldState, "Password deleted successfully")
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Default.Delete,
                                    contentDescription = null,
                                    tint = White
                                )
                            }
                            Spacer(Modifier.width(20.dp))
                            OutlinedButton(
                                shape = RoundedCornerShape(100.dp),
                                border = BorderStroke(2.dp, if (passwordStatus == ACTIVE) White else greenColor),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = if (passwordStatus == ACTIVE) White else greenColor
                                ),
                                onClick = {
                                    if (passwordStatus == Status.DELETED) {
                                        // TODO: REQUEST THEN
                                        showSnack(coroutineScope, scaffoldState, "Password recovered successfully")
                                    } else {
                                        val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
                                        clipboard.setContents(StringSelection(password.password), null)
                                        showSnack(coroutineScope, scaffoldState, "Password copied successfully")
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (passwordStatus == ACTIVE) Default.ContentCopy else Default.Restore,
                                    contentDescription = null,
                                    tint = if (passwordStatus == ACTIVE) primaryColor else White
                                )
                            }
                        }
                    }
                }
                Divider(Modifier.padding(top = 30.dp), thickness = 1.dp, color = White)
                Row {
                    Column {
                        Text(
                            modifier = Modifier.padding(start = 35.dp, top = 10.dp),
                            text = "Password",
                            color = White,
                            fontSize = 30.sp
                        )
                        Row(Modifier.fillMaxWidth()) {
                            TextButton(
                                onClick = { hidePassword.value = !hidePassword.value }
                            ) {
                                Text(
                                    modifier = Modifier.padding(start = 45.dp, top = 10.dp),
                                    text = if (!hidePassword.value) password.password else hidePassword(password),
                                    color = White,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
                Divider(Modifier.padding(top = 30.dp), thickness = 1.dp, color = White)
                Row {
                    Column {
                        Row(
                            modifier = Modifier.padding(start = 35.dp, top = 10.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Row {
                                Text(
                                    text = "Scopes",
                                    color = White,
                                    fontSize = 30.sp
                                )
                            }
                            if (passwordStatus == ACTIVE) {
                                Spacer(Modifier.width(10.dp))
                                Row {
                                    OutlinedButton(
                                        shape = RoundedCornerShape(100),
                                        border = BorderStroke(2.dp, White),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = White
                                        ),
                                        onClick = {
                                            // TODO: REQUEST THEN
                                            showSnack(coroutineScope, scaffoldState, "Scope added successfully")
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
                        LazyVerticalGrid(
                            modifier = Modifier.padding(start = 45.dp, top = 10.dp),
                            columns = GridCells.Adaptive(140.dp),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(password.scopesSorted.toList()) { scope ->
                                TextButton(
                                    onClick = {
                                        if (passwordStatus == ACTIVE) {
                                            // TODO: REQUEST THEN
                                            showSnack(coroutineScope, scaffoldState, "Scope edited")
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = White
                                    ),
                                    shape = RoundedCornerShape(100)
                                ) {
                                    Row {
                                        Column(Modifier.weight(1f).fillMaxHeight().padding(start = 5.dp)) {
                                            Text(scope)
                                        }
                                        if (passwordStatus == ACTIVE) {
                                            Column(
                                                modifier = Modifier.weight(1f).fillMaxHeight(),
                                                horizontalAlignment = Alignment.End,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Icon(
                                                    modifier = Modifier.size(20.dp).clickable {
                                                        // TODO: REQUEST THEN
                                                        showSnack(coroutineScope, scaffoldState, "Scope deleted")
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
                        Divider(thickness = 1.dp, color = White)
                    }
                }
            }
        }
    }

    /**
     * Method to hide a password
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