package layouts.components.forms

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecknobit.glider.helpers.GliderLauncher
import com.tecknobit.glider.records.Password.*
import helpers.*
import layouts.components.GliderButton
import layouts.components.GliderTextField
import layouts.components.sections.Sidebar
import org.json.JSONObject

/**
 * **InsertForm** is the view where the user can insert a new password
 *
 * @author Tecknobit - N7ghtm4r3
 * @see RequestManager
 * @see GliderForm
 * **/
class InsertForm : GliderForm() {

    /**
     * Method to create the layout to insert a new password
     */
    fun insertPassword() {
        fillPopupContent {
            tail = rememberSaveable { mutableStateOf("") }
            scopes = rememberSaveable { mutableStateOf("") }
            var password by rememberSaveable { mutableStateOf("") }
            createTitleLayout("Insert a new password")
            Column(
                modifier = Modifier.fillMaxSize().padding(top = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                createTailInput()
                Spacer(Modifier.height(10.dp))
                GliderTextField(
                    isError = errorTriggered[1],
                    text = "Password",
                    value = password,
                    onChange = {
                        password = it.replace(" ", "")
                        errorTriggered[1] = password.isEmpty()
                    },
                    leadingIcon = Icons.Default.Info,
                    leadingOnClick = { showPopupSnack("Custom password to insert in Glider") },
                    trailingIcon = Icons.Default.Clear,
                    trailingOnClick = { password = "" }
                )
                createScopesInput()
                Spacer(Modifier.height(20.dp))
                GliderButton(
                    onClick = {
                        setRequestPayload(GliderLauncher.Operation.INSERT_PASSWORD, tail.value, password, scopes.value)
                        if (payload != null) {
                            socketManager!!.writeContent(payload)
                            response = JSONObject(socketManager!!.readContent())
                            if (successfulResponse()) {
                                dismissPopup()
                                showSnack(Sidebar.coroutineScope, Sidebar.scaffoldState, "Password inserted")
                            } else
                                showPopupSnack("Operation failed")
                        }
                    },
                    text = "Insert"
                )
            }
        }
        showPopup()
    }

    /**
     * Method to create the payload for the [GliderLauncher.Operation.INSERT_PASSWORD] request
     *
     * @param operation the operation to perform
     * @param params dynamic params list to attach to the [payload]
     */
    override fun <T> setRequestPayload(operation: GliderLauncher.Operation?, vararg params: T) {
        super.setRequestPayload(operation, *params)
        if (params[0].toString().isNotEmpty()) {
            payload!!.put(PasswordKeys.tail.name, params[0])
            val password = params[1].toString()
            val lPassword = password.length
            if (password.isNotEmpty()) {
                if (lPassword in PASSWORD_MIN_LENGTH..PASSWORD_MAX_LENGTH) {
                    payload!!.put(PasswordKeys.password.name, password)
                    payload!!.put(PasswordKeys.scopes.name, params[2].toString().split(","))
                } else {
                    errorTriggered[1] = true
                    showPopupSnack("The password length must be between 8 and 32 characters length")
                    payload = null
                }
            } else {
                errorTriggered[1] = true
                showPopupSnack("Password is required")
                payload = null
            }
        } else {
            errorTriggered[0] = true
            showPopupSnack("You must fill the tail field first")
            payload = null
        }
    }

}