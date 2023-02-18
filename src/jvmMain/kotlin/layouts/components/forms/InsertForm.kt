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
import helpers.*
import layouts.components.GliderButton
import layouts.components.GliderTextField

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
                        // TODO: REQUEST THEN
                        dismissPopup()
                    },
                    text = "Insert"
                )
            }
        }
        showPopup()
    }

}