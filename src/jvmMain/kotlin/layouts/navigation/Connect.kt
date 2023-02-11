package layouts.navigation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.unit.dp
import helpers.backgroundColor
import helpers.fromHexToColor
import helpers.primaryColor
import layouts.components.GliderButton
import layouts.components.GliderTextField
import moe.tlaster.precompose.navigation.Navigator

/**
 * This is the layout for the connect screen
 *
 * @author Tecknobit - N7ghtm4r3
 * **/
class Connect {

    /**
     * Method to create the [Connect] view
     * @param navigator useful to navigate in the app
     * @param modifier modifier for the layout
     */
    @Composable
    @Preview
    fun connect(navigator: Navigator, modifier: Modifier = Modifier) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.background(fromHexToColor("#1b1ba6")).fillMaxSize()
        ) {
            Card(
                modifier = Modifier.align(alignment = Alignment.Center).width(920.dp).height(720.dp),
                elevation = 10.dp,
                backgroundColor = backgroundColor,
                shape = RoundedCornerShape(10.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Row {
                        Column(
                            modifier = Modifier.weight(1f).fillMaxHeight().background(primaryColor),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(100.dp))
                            Card(
                                modifier = Modifier.width(100.dp).height(100.dp),
                                elevation = 10.dp,
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Image(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource("logo.png"),
                                    contentDescription = null
                                )
                            }
                            Spacer(modifier = Modifier.height(50.dp))
                            var host by rememberSaveable { mutableStateOf("") }
                            GliderTextField(
                                text = "Host",
                                value = host,
                                onChange = {
                                    host = it
                                },
                                trailingIcon = Default.Clear,
                                trailingOnClick = {
                                    host = ""
                                }
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            var password by rememberSaveable { mutableStateOf("") }
                            var isVisible by rememberSaveable { mutableStateOf(false) }
                            GliderTextField(
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                visualTransformation = if (isVisible) None else PasswordVisualTransformation(),
                                text = "Password",
                                value = password,
                                onChange = {
                                    password = it
                                },
                                trailingIcon = if (isVisible) Default.VisibilityOff else Default.Visibility,
                                trailingOnClick = {
                                    isVisible = !isVisible
                                }
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            var port by rememberSaveable { mutableStateOf("") }
                            GliderTextField(
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                text = "Port",
                                value = port,
                                onChange = {
                                    port = it
                                },
                                trailingIcon = Default.Clear,
                                trailingOnClick = {
                                    port = ""
                                }
                            )
                            Spacer(modifier = Modifier.height(50.dp))
                            GliderButton(
                                onClick = {},
                                text = "Connect"
                            )
                        }
                    }
                }
            }
        }
    }

}
