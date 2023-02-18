package layouts.components.forms

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import helpers.RequestManager
import helpers.primaryColor
import helpers.showPopupSnack
import layouts.components.GliderTextField

/**
 * **GliderForm** is the super class where all the views inheriting are enabled to perform a request with your
 * own **Glider's** backend service filling the form with the view
 *
 * @author Tecknobit - N7ghtm4r3
 * @see RequestManager
 * **/
open class GliderForm : RequestManager() {

    /**
     * **tail** -> the text field to get the tail of the password
     */
    protected lateinit var tail: MutableState<String>

    /**
     * **scopes** -> the text field to get the scopes of the password
     */
    protected lateinit var scopes: MutableState<String>

    /**
     * **errorTriggered** -> the list of the triggers for the input fields
     */
    protected var errorTriggered: MutableList<Boolean> = mutableListOf(false, false)

    /**
     * Method to create the title layout
     *
     * @param title: title text for the popup
     */
    @Composable
    protected fun createTitleLayout(title: String) {
        Text(
            modifier = Modifier.padding(start = 45.dp, top = if (title.contains("Create")) 50.dp else 35.dp),
            text = title,
            fontSize = 20.sp,
            color = primaryColor
        )
    }

    /**
     * Method to create the tail layout. No-any params required
     */
    @Composable
    protected fun createTailInput() {
        Spacer(Modifier.height(20.dp))
        GliderTextField(
            isError = errorTriggered[0],
            text = "Tail",
            value = tail.value,
            onChange = {
                tail.value = it.replace(" ", "")
                errorTriggered[0] = tail.value.isEmpty()
            },
            leadingIcon = Icons.Default.Info,
            leadingOnClick = { showPopupSnack("Tail for the password es. TraderBot") },
            trailingIcon = Icons.Default.Clear,
            trailingOnClick = { tail.value = "" }
        )
    }

    /**
     * Method to create the scopes layout. No-any params required
     */
    @Composable
    protected fun createScopesInput() {
        Spacer(Modifier.height(10.dp))
        GliderTextField(
            text = "Scopes",
            value = scopes.value,
            onChange = { scopes.value = it.replace(" ", "") },
            leadingIcon = Icons.Default.Info,
            leadingOnClick = { showPopupSnack("Scopes where use the password, they must be divided by ,") },
            trailingIcon = Icons.Default.Clear,
            trailingOnClick = { scopes.value = "" }
        )
    }

}