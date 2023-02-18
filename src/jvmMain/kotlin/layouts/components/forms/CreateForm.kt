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
import androidx.compose.ui.unit.sp
import helpers.*
import layouts.components.GliderButton
import layouts.components.GliderTextField

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
                        isError = errorTriggered[1],
                        text = "Length",
                        value = length,
                        onChange = {
                            length = it.replace(" ", "")
                            errorTriggered[1] = length.isEmpty()
                        },
                        leadingIcon = Icons.Default.Info,
                        leadingOnClick = { showPopupSnack("Length of the password to create") },
                        trailingIcon = Icons.Default.Clear,
                        trailingOnClick = {
                            length = ""
                        }
                    )
                    Spacer(Modifier.width(10.dp))
                    GliderButton(
                        modifier = Modifier.width(90.dp).padding(top = 7.dp),
                        onClick = {
                            // TODO: REQUEST THEN
                            dismissPopup()
                        },
                        text = "Create",
                        textSize = 16.5.sp
                    )
                }
            }
        }
        showPopup()
    }

}