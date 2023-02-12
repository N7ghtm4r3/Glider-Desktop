package layouts.navigation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardType.Companion.Password
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecknobit.glider.helpers.GliderLauncher.Operation
import com.tecknobit.glider.helpers.GliderLauncher.Operation.CONNECT
import com.tecknobit.glider.records.Session.SessionKeys.*
import helpers.*
import kotlinx.coroutines.CoroutineScope
import layouts.components.GliderButton
import layouts.components.GliderText
import layouts.components.GliderTextField
import layouts.parents.GliderScreen
import moe.tlaster.precompose.navigation.Navigator
import java.awt.Desktop
import java.net.URI

/**
 * This is the layout for the connect screen
 *
 * @author Tecknobit - N7ghtm4r3
 * @see GliderScreen
 * **/
class Connect : GliderScreen() {

    /**
     * **errorTriggered** -> the list of the triggers for the input fields
     */
    private lateinit var errorTriggered: MutableList<Boolean>

    /**
     * **scaffoldState** -> the scaffold state for the scaffold of the page
     */
    private lateinit var scaffoldState: ScaffoldState

    /**
     * **scope** -> the coroutine scope to manage the coroutines of the [scaffoldState]
     */
    private lateinit var scope: CoroutineScope

    /**
     * Method to create the [Connect] view
     * @param navigator useful to navigate in the app
     * @param modifier modifier for the layout
     */
    @Composable
    @Preview
    fun connect(navigator: Navigator, modifier: Modifier = Modifier) {
        errorTriggered = remember { mutableStateListOf(false, false, false) }
        scaffoldState = rememberScaffoldState()
        scope = rememberCoroutineScope()
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.background(fromHexToColor("#1b1ba6")).fillMaxSize()
        ) {
            Card(
                modifier = Modifier.align(alignment = Alignment.Center).width(900.dp).height(700.dp),
                elevation = 10.dp,
                backgroundColor = backgroundColor,
                shape = RoundedCornerShape(15.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Row {
                        Column(
                            modifier = Modifier.weight(1f).fillMaxHeight().background(primaryColor),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(Modifier.height(85.dp))
                            GliderText(
                                modifier = Modifier.align(Alignment.Start).padding(start = 40.dp),
                                text = appName,
                                size = 45.sp
                            )
                            Spacer(Modifier.height(5.dp))
                            GliderText(
                                modifier = Modifier.align(Alignment.Start).padding(start = 45.dp, end = 25.dp),
                                text = "This is an open source project useful to manage the creation and the " +
                                        "storage of your passwords with the Glider ecosystem",
                                size = 20.sp
                            )
                            Box(
                                modifier = Modifier.fillMaxSize().padding(bottom = 100.dp),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                Row {
                                    Column {
                                        IconButton(
                                            modifier = Modifier.size(40.dp),
                                            onClick = { openLink("https://play.google.com/store/apps/details?id=com.tecknobit.glider") }
                                        ) {
                                            Image(
                                                painter = painterResource("android.png"),
                                                contentDescription = null
                                            )
                                        }
                                    }
                                    Spacer(Modifier.width(85.dp))
                                    Column {
                                        IconButton(
                                            modifier = Modifier.size(40.dp),
                                            onClick = { openLink("https://github.com/N7ghtm4r3/Glider#readme") }
                                        ) {
                                            Image(
                                                painter = painterResource("git.svg"),
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Scaffold(
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            scaffoldState = scaffoldState,
                            snackbarHost = {
                                SnackbarHost(it) { data ->
                                    Snackbar(
                                        modifier = modifier,
                                        backgroundColor = backgroundColor,
                                        contentColor = primaryColor,
                                        snackbarData = data
                                    )
                                }
                            }
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize().background(backgroundColor),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.height(100.dp))
                                Card(
                                    modifier = Modifier.size(100.dp),
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Image(
                                        modifier = Modifier.fillMaxSize(),
                                        painter = painterResource("logo.png"),
                                        contentDescription = null
                                    )
                                }
                                Spacer(Modifier.height(50.dp))
                                var host by rememberSaveable { mutableStateOf("") }
                                GliderTextField(
                                    text = "Host",
                                    value = host,
                                    isError = errorTriggered[0],
                                    onChange = {
                                        host = it
                                        errorTriggered[0] = host.isEmpty()
                                    },
                                    leadingIcon = Default.Info,
                                    leadingOnClick = {
                                        showSnack(
                                            scope,
                                            scaffoldState,
                                            "Host address where you started the service"
                                        )
                                    },
                                    trailingIcon = Default.Clear,
                                    trailingOnClick = {
                                        host = ""
                                    }
                                )
                                Spacer(Modifier.height(30.dp))
                                var password by rememberSaveable { mutableStateOf("") }
                                var isVisible by rememberSaveable { mutableStateOf(false) }
                                GliderTextField(
                                    keyboardOptions = KeyboardOptions(keyboardType = Password),
                                    visualTransformation = if (isVisible) None else PasswordVisualTransformation(),
                                    text = "Password",
                                    value = password,
                                    isError = errorTriggered[1],
                                    onChange = {
                                        password = it
                                        errorTriggered[1] = password.isEmpty()
                                    },
                                    leadingIcon = Default.Info,
                                    leadingOnClick = {
                                        showSnack(
                                            scope,
                                            scaffoldState,
                                            "Password you choose to connect to the service"
                                        )
                                    },
                                    trailingIcon = if (isVisible) Default.VisibilityOff else Default.Visibility,
                                    trailingOnClick = {
                                        isVisible = !isVisible
                                    }
                                )
                                Spacer(Modifier.height(30.dp))
                                var port by rememberSaveable { mutableStateOf("") }
                                GliderTextField(
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    text = "Port",
                                    value = port,
                                    isError = errorTriggered[2],
                                    onChange = {
                                        port = it
                                        errorTriggered[2] = port.isEmpty()
                                    },
                                    leadingIcon = Default.Info,
                                    leadingOnClick = {
                                        showSnack(
                                            scope,
                                            scaffoldState,
                                            "Host port where you started the service"
                                        )
                                    },
                                    trailingIcon = Default.Clear,
                                    trailingOnClick = {
                                        port = ""
                                    }
                                )
                                Spacer(Modifier.height(50.dp))
                                GliderButton(
                                    onClick = {
                                        setRequestPayload(CONNECT, host, password, port)
                                        if (payload != null) {
                                            // TODO: REQUEST THEN
                                        }
                                    },
                                    text = "Connect"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Method to create the payload for a request
     *
     * @param operation the operation to perform
     * @param params dynamic params list to attach to the [payload]
     */
    override fun <T : Any?> setRequestPayload(operation: Operation?, vararg params: T) {
        super.setRequestPayload(operation, *params)
        if (params[0].toString().isNotBlank()) {
            payload!!.put(hostAddress.name, params[0])
            if (params[1].toString().isNotBlank()) {
                payload!!.put(sessionPassword.name, params[1])
                try {
                    payload!!.put(hostPort.name, Integer.parseInt(params[2].toString()))
                } catch (e: NumberFormatException) {
                    errorTriggered[2] = true
                    showSnack(scope, scaffoldState, "You need to insert a valid port first")
                    payload = null
                }
            } else {
                errorTriggered[1] = true
                showSnack(scope, scaffoldState, "You need to insert a valid password first")
                payload = null
            }
        } else {
            errorTriggered[0] = true
            showSnack(scope, scaffoldState, "You need to insert a valid host address first")
            payload = null
        }
    }

    /**
     * Method to open link from the application
     * @param link: link to open
     */
    private fun openLink(link: String) {
        Desktop.getDesktop().browse(URI.create(link))
    }

}
