package layouts.components.forms

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecknobit.glider.helpers.GliderLauncher
import com.tecknobit.glider.helpers.GliderLauncher.Operation.CREATE_PASSWORD
import com.tecknobit.glider.records.Password.*
import helpers.*
import layouts.components.GliderButton
import layouts.components.GliderTextField
import layouts.components.sections.Sidebar.Companion.coroutineScope
import layouts.components.sections.Sidebar.Companion.scaffoldState
import org.json.JSONArray
import org.json.JSONObject

/**
 * **CreateForm** is the view where the user can create a new password
 *
 * @author Tecknobit - N7ghtm4r3
 * @see RequestManager
 * @see GliderForm
 * **/
class CreateForm : GliderForm() {

    /**
     * Method to create the layout to create a new password
     */
    fun createPassword() {
        fillPopupContent {
            tail = rememberSaveable { mutableStateOf("") }
            scopes = rememberSaveable { mutableStateOf("") }
            var length by rememberSaveable { mutableStateOf("") }
            createTitleLayout("Create a new password")
            Column(
                modifier = Modifier.fillMaxSize().padding(top = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                createTailInput()
                createScopesInput()
                Spacer(Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    GliderTextField(
                        modifier = Modifier.width(180.dp),
                        isError = errorTriggered[2],
                        text = "Length",
                        value = length,
                        onChange = {
                            length = it.replace(" ", "")
                            errorTriggered[2] = length.isEmpty()
                        },
                        leadingIcon = Default.Info,
                        leadingOnClick = { showPopupSnack("Length of the password to create") },
                        trailingIcon = Default.Clear,
                        trailingOnClick = {
                            length = ""
                        }
                    )
                    Spacer(Modifier.width(10.dp))
                    GliderButton(
                        modifier = Modifier.width(90.dp).padding(top = 7.dp),
                        onClick = {
                            setRequestPayload(CREATE_PASSWORD, tail.value, scopes.value, length)
                            if (payload != null) {
                                socketManager!!.writeContent(payload)
                                response = JSONObject(socketManager!!.readContent())
                                if (successfulResponse()) {
                                    dismissPopup()
                                    showSnack(coroutineScope, scaffoldState, "Password created")
                                } else
                                    showPopupSnack("Operation failed")
                            }
                        },
                        text = "Create",
                        textSize = 16.5.sp
                    )
                }
            }
        }
        showPopup()
    }

    /**
     * Method to create the payload for the [GliderLauncher.Operation.CREATE_PASSWORD] request
     *
     * @param operation the operation to perform
     * @param params dynamic params list to attach to the [payload]
     */
    override fun <T> setRequestPayload(operation: GliderLauncher.Operation?, vararg params: T) {
        super.setRequestPayload(operation, *params)
        if (tail.value.isNotEmpty()) {
            payload!!.put(PasswordKeys.tail.name, params[0])
            try {
                val pLength = Integer.parseInt(params[2].toString())
                if (pLength in PASSWORD_MIN_LENGTH..PASSWORD_MAX_LENGTH) {
                    payload!!.put(PasswordKeys.length.name, pLength)
                    payload!!.put(PasswordKeys.scopes.name, JSONArray(params[1].toString().split(",")))
                } else {
                    errorTriggered[2] = true
                    showPopupSnack("The password length must be between 8 and 32 characters length")
                    payload = null
                }
            } catch (e: NumberFormatException) {
                errorTriggered[2] = true
                showPopupSnack("You must fill the length field first")
                payload = null
            }
        } else {
            errorTriggered[0] = true
            showPopupSnack("You must fill the tail field first")
            payload = null
        }
    }

}