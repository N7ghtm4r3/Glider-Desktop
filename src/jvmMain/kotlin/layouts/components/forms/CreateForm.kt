package layouts.components.forms

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
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
import helpers.dismissPopup
import helpers.fillPopupContent
import helpers.primaryColor
import helpers.showPopup
import layouts.components.GliderButton
import layouts.components.GliderTextField

class CreateForm : GliderForm() {

    fun createPassword() {
        fillPopupContent {
            var tail by rememberSaveable { mutableStateOf("") }
            var scopes by rememberSaveable { mutableStateOf("") }
            var length by rememberSaveable { mutableStateOf("") }
            Text(
                modifier = Modifier.padding(start = 25.dp, top = 25.dp),
                text = "Create a new password",
                fontSize = 20.sp,
                color = primaryColor
            )
            Column(
                modifier = Modifier.fillMaxSize().padding(top = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.height(20.dp))
                GliderTextField(
                    isError = errorTriggered[0],
                    text = "Tail",
                    value = tail,
                    onChange = {
                        tail = it.replace(" ", "")
                        errorTriggered[0] = tail.isEmpty()
                    },
                    leadingIcon = Icons.Default.Info,
                    leadingOnClick = {
                        /*showSnack(
                            scope,
                            scaffoldState,
                            "Host address where you started the service"
                        )*/
                    },
                    trailingIcon = Icons.Default.Clear,
                    trailingOnClick = {
                        tail = ""
                    }
                )
                Spacer(Modifier.height(10.dp))
                GliderTextField(
                    text = "Scopes",
                    value = scopes,
                    onChange = { scopes = it.replace(" ", "") },
                    leadingIcon = Icons.Default.Info,
                    leadingOnClick = {
                        /*showSnack(
                            scope,
                            scaffoldState,
                            "Host address where you started the service"
                        )*/
                    },
                    trailingIcon = Icons.Default.Clear,
                    trailingOnClick = {
                        scopes = ""
                    }
                )
                Spacer(Modifier.height(10.dp))
                GliderTextField(
                    isError = errorTriggered[1],
                    text = "Length",
                    value = length,
                    onChange = {
                        length = it.replace(" ", "")
                        errorTriggered[1] = length.isEmpty()
                    },
                    leadingIcon = Icons.Default.Info,
                    leadingOnClick = {
                        /*showSnack(
                            scope,
                            scaffoldState,
                            "Host address where you started the service"
                        )*/
                    },
                    trailingIcon = Icons.Default.Clear,
                    trailingOnClick = {
                        length = ""
                    }
                )
                GliderButton(
                    modifier = Modifier.padding(top = 25.dp),
                    onClick = {
                        // TODO: REQUEST THEN
                        dismissPopup()
                    },
                    text = "Create"
                )
            }
        }
        showPopup()
    }

}