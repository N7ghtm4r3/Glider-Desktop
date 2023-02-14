package layouts.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.tecknobit.glider.records.Password
import com.tecknobit.glider.records.Password.Status.ACTIVE
import helpers.backgroundColor
import helpers.primaryColor
import helpers.showSnack
import kotlinx.coroutines.CoroutineScope
import layouts.parents.RequestManager

class PasswordItem : RequestManager() {

    private lateinit var scaffoldState: ScaffoldState
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var password: Password

    @Composable
    fun passwordItem(password: Password, scaffoldState: ScaffoldState) {
        this.scaffoldState = scaffoldState
        coroutineScope = rememberCoroutineScope()
        this.password = password
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.width(400.dp).height(250.dp).padding(10.dp),
                backgroundColor = Color.White,
                elevation = 10.dp,
                shape = RoundedCornerShape(15.dp)
            ) {
                Column(
                    modifier = Modifier.padding(top = 10.dp, end = 15.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                            text = password.tail,
                            color = primaryColor,
                            fontSize = 20.sp
                        )
                    }
                    Divider(thickness = 1.dp, color = backgroundColor)
                    var expanded by remember { mutableStateOf(false) }
                    var mSelectedText by remember { mutableStateOf("Scopes") }
                    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
                    Row {
                        OutlinedTextField(
                            value = mSelectedText,
                            readOnly = true,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = primaryColor,
                                unfocusedLabelColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                focusedLabelColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent
                            ),
                            onValueChange = { mSelectedText = it },
                            modifier = Modifier.padding(start = 10.dp).fillMaxWidth()
                                .onGloballyPositioned { coordinates -> mTextFieldSize = coordinates.size.toSize() },
                            trailingIcon = {
                                Icon(
                                    imageVector = if (expanded) Filled.KeyboardArrowUp else Filled.KeyboardArrowDown,
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        expanded = !expanded
                                    }
                                )
                            }
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
                        ) {
                            password.scopes.forEach { label ->
                                DropdownMenuItem(
                                    contentPadding = PaddingValues(start = 10.dp),
                                    onClick = {
                                        mSelectedText = label
                                        expanded = false
                                    }
                                ) {
                                    Text(text = label)
                                }
                            }
                        }
                    }
                    Divider(thickness = 1.dp, color = backgroundColor)
                    Row(
                        Modifier.padding(top = 10.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 15.dp, top = 10.dp),
                            text = password.password,
                            color = primaryColor,
                            fontSize = 18.sp
                        )
                    }
                    Divider(thickness = 1.dp, color = backgroundColor)
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Row {
                            Column(
                                modifier = Modifier.weight(1f).fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TextButton(
                                    modifier = Modifier.fillMaxSize().height(30.dp),
                                    onClick = { deletePassword() }
                                ) {
                                    Text(
                                        text = "Delete",
                                        fontSize = 19.sp
                                    )
                                }
                            }
                            Spacer(Modifier.width(20.dp))
                            Column(
                                modifier = Modifier.weight(1f).fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TextButton(
                                    modifier = Modifier.fillMaxSize().height(30.dp),
                                    onClick = { performPasswordAction() }
                                ) {
                                    Text(
                                        text = if (password.status.equals(ACTIVE)) "Copy" else "Recover",
                                        fontSize = 19.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun deletePassword() {
        showSnack(
            coroutineScope,
            scaffoldState,
            if (password.status.equals(ACTIVE)) "Delete" else "Permanently deleted"
        )
    }

    fun performPasswordAction() {
        showSnack(coroutineScope, scaffoldState, if (password.status.equals(ACTIVE)) "Copy" else "Recover")
    }

}