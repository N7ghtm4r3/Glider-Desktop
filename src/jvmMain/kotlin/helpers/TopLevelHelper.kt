package helpers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

/**
 * `showAlert` whether show the alert
 */
lateinit var showAlert: MutableState<Boolean>

/**
 * `alertContent` content of the alert to show
 */
lateinit var alertContent: AlertContent

/**
 * `showPopup` whether show the popup
 */
lateinit var showPopup: MutableState<Boolean>

/**
 * `popupContent` content of the popup to show
 */
lateinit var popupContent: @Composable BoxScope.() -> Unit

/**
 * Method to fill the content for the alert
 *
 * @param modifier Modifier to be applied to the layout of the dialog.
 * @param dismissRequest: callback that will be called when the user closes the dialog
 * @param title: The title of the Dialog which should specify the purpose of the Dialog. The title
 * is not mandatory, because there may be sufficient information inside the [text]. Provided text
 * style will be [Typography.subtitle1].
 * @param text: The text which presents the details regarding the Dialog's purpose. Provided text
 * style will be [Typography.body2].
 * @param dismissAction: action to execute when the alert has been dismissed
 * @param confirmText: text for the confirm button
 * @param confirmAction: action to execute when the alert has been confirmed
 */
fun fillAlertContent(
    modifier: Modifier = Modifier.width(400.dp).height(200.dp), dismissRequest: () -> Unit = {},
    title: String, text: @Composable (() -> Unit), dismissAction: () -> Unit = { dismissAlert() },
    confirmText: String = "Confirm", confirmAction: () -> Unit
) {
    alertContent = AlertContent(modifier, dismissRequest, title, text, dismissAction, confirmText, confirmAction)
}

/**
 * Method to set the [showAlert] value on **true** to show the alert. No-any params required
 */
fun showAlert() {
    showAlert.value = true
}

/**
 * Method to set the [showAlert] value on **false** to dismiss the alert. No-any params required
 */
fun dismissAlert() {
    showAlert.value = false
}

/**
 * Method create the alert and show it. No-any params required
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun createAlert() {
    AlertDialog(
        modifier = alertContent.modifier,
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color.White,
        onDismissRequest = alertContent.dismissRequest,
        title = {
            Text(
                text = alertContent.title,
                color = primaryColor
            )
        },
        text = alertContent.text,
        dismissButton = {
            TextButton(
                onClick = alertContent.dismissAction
            ) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(
                onClick = alertContent.confirmAction
            ) {
                Text(alertContent.confirmText)
            }
        },
    )
}

/**
 * Method to fill the content for the popup
 *
 * @param content content of the popup to show
 */
fun fillPopupContent(content: @Composable BoxScope.() -> Unit) {
    popupContent = content
}

/**
 * Method to set the [showPopup] value on **true** to show the popup. No-any params required
 */
fun showPopup() {
    showPopup.value = true
}

/**
 * Method to set the [showPopup] value on **false** to dismiss the popup. No-any params required
 */
fun dismissPopup() {
    showPopup.value = false
}

/**
 * Method create the popup and show it. No-any params required
 */
@Composable
fun createPopup() {
    Popup(
        alignment = Alignment.Center,
        focusable = true
    ) {
        Card(
            Modifier.width(400.dp).height(400.dp),
            shape = RoundedCornerShape(15.dp),
            backgroundColor = Color.White,
            elevation = 10.dp
        ) {
            Box {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        modifier = Modifier.padding(end = 5.dp),
                        onClick = { showPopup.value = false }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            tint = primaryColor
                        )
                    }
                }
                Box(content = popupContent)
            }
        }
    }
}

/**
 * The [AlertContent] is class useful to store the content information fon an alert
 *
 * @param modifier Modifier to be applied to the layout of the dialog.
 * @param dismissRequest: callback that will be called when the user closes the dialog
 * @param title The title of the Dialog which should specify the purpose of the Dialog. The title
 * is not mandatory, because there may be sufficient information inside the [text]. Provided text
 * style will be [Typography.subtitle1].
 * @param text The text which presents the details regarding the Dialog's purpose. Provided text
 * style will be [Typography.body2].
 * @param dismissAction: action to execute when the alert has been dismissed
 * @param confirmText: text for the confirm button
 * @param confirmAction: action to execute when the alert has been confirmed
 *
 * @author Tecknobit - N7ghtm4r3
 */
class AlertContent(
    var modifier: Modifier = Modifier.width(400.dp).height(200.dp),
    var dismissRequest: () -> Unit = {},
    var title: String,
    var text: @Composable (() -> Unit),
    var dismissAction: () -> Unit = { dismissAlert() },
    var confirmText: String = "Confirm",
    var confirmAction: () -> Unit
)