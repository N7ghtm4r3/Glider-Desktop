@file:Suppress("UNCHECKED_CAST")

package layouts.components.sections.lists

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecknobit.glider.records.Password
import com.tecknobit.glider.records.Password.Status.ACTIVE
import com.tecknobit.glider.records.Password.Status.DELETED
import helpers.User.Companion.passwords
import helpers.greenColor
import helpers.primaryColor
import helpers.redColor
import layouts.components.GliderTextField
import layouts.components.sections.tabs.PasswordTab

/**
 * This is the layout for the list section where there is the list of the passwords
 *
 * @author Tecknobit - N7ghtm4r3
 * @see List
 * **/
class PasswordsList : List() {

    /**
     * **lSelected** -> trigger flag to filter the [ACTIVE] passwords
     */
    private lateinit var lSelected: MutableState<Boolean>

    /**
     * **rSelected** -> trigger flag to filter the [DELETED] passwords
     */
    private lateinit var rSelected: MutableState<Boolean>

    /**
     * **querySearch** -> the query to filter the passwords by tail or scopes
     */
    private lateinit var querySearch: MutableState<String>

    /**
     * **passwordTab** -> manager of the [PasswordTab]
     */
    private lateinit var passwordTab: PasswordTab

    /**
     * **filteredPasswords** -> list of the filtered [Password]
     */
    private var filteredPasswords: MutableList<Password> = mutableStateListOf()

    /**
     * **loadFirstItem** -> whether load the first of the list
     */
    private var loadFirstItem: Boolean = true

    /**
     * Method to create the [PasswordsList] view. No-any params required
     */
    @Composable
    fun showPasswords() {
        lSelected = remember { mutableStateOf(true) }
        rSelected = remember { mutableStateOf(false) }
        querySearch = remember { mutableStateOf("") }
        selectedItem = remember { mutableStateOf(null) }
        passwordTab = remember { PasswordTab() }
        itemsList.clear()
        itemsList.addAll(passwords)
        Row {
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight().padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (itemsList.size > 0) {
                    Column {
                        GliderTextField(
                            modifier = Modifier.height(60.dp),
                            text = "Filter by tail or scopes",
                            value = querySearch.value,
                            onChange = {
                                querySearch.value = it
                            },
                            leadingIcon = Icons.Default.Search,
                            trailingIcon = Icons.Default.Clear,
                            trailingOnClick = {
                                querySearch.value = ""
                            }
                        )
                    }
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 25.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Row {
                                OutlinedButton(
                                    shape = RoundedCornerShape(100.dp),
                                    border = BorderStroke(2.dp, greenColor),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = if (lSelected.value) greenColor else Color.Transparent
                                    ),
                                    onClick = {
                                        lSelected.value = !lSelected.value
                                        rSelected.value = !rSelected.value
                                        loadFirstItem = true
                                        passwordTab.hidePassword()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ViewList,
                                        contentDescription = null,
                                        tint = if (!lSelected.value) greenColor else Color.White
                                    )
                                }
                            }
                            Spacer(Modifier.width(25.dp))
                            Row {
                                OutlinedButton(
                                    shape = RoundedCornerShape(100.dp),
                                    border = BorderStroke(2.dp, redColor),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = if (rSelected.value) redColor else Color.Transparent
                                    ),
                                    onClick = {
                                        rSelected.value = !rSelected.value
                                        lSelected.value = !lSelected.value
                                        loadFirstItem = true
                                        passwordTab.hidePassword()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = if (!rSelected.value) redColor else Color.White
                                    )
                                }
                            }
                        }
                    }
                    filteredPasswords = filterPasswords(itemsList as MutableList<Password>)
                    if (loadFirstItem && filteredPasswords.size > 0)
                        selectedItem.value = filteredPasswords[0]
                    if (filteredPasswords.size > 0) {
                        loadList {
                            items(filteredPasswords) { password ->
                                Card(
                                    modifier = Modifier.fillMaxWidth().height(65.dp).clickable {
                                        selectedItem.value = password
                                        passwordTab.hidePassword()
                                        loadFirstItem = false
                                    },
                                    backgroundColor = Color.White,
                                    shape = RoundedCornerShape(10.dp),
                                    elevation = 5.dp
                                ) {
                                    Row {
                                        Column(
                                            modifier = Modifier.weight(1f).fillMaxHeight(),
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                modifier = Modifier.padding(start = 10.dp),
                                                text = password.tail,
                                                color = primaryColor,
                                                fontSize = 20.sp
                                            )
                                        }
                                        Column(
                                            modifier = Modifier.weight(1f).fillMaxHeight(),
                                            horizontalAlignment = Alignment.End,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Box(
                                                modifier = Modifier.background(
                                                    if (password.status == ACTIVE) greenColor
                                                    else redColor
                                                ).fillMaxHeight().width(100.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else
                        selectedItem.value = null
                }
            }
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight().background(primaryColor)
            ) {
                createTab()
            }
        }
    }

    /**
     * Method to load the passwords list
     * @param currentPasswords: the current passwords list to filter
     * @return the list of the passwords filtered as [MutableList] of [Password]
     */
    private fun filterPasswords(currentPasswords: MutableList<Password>): MutableList<Password> {
        val passwords = mutableStateListOf<Password>()
        currentPasswords.forEach { password ->
            val sPassword = password.status
            if ((lSelected.value && sPassword == ACTIVE) || (rSelected.value && sPassword == DELETED)) {
                val query = querySearch.value.replace(" ", "")
                if (query.isNotEmpty()) {
                    val sScopes = password.scopesSorted.toTypedArray().contentToString()
                    if (!query.contains(",")) {
                        if (password.tail.contains(query) || sScopes.contains(query))
                            passwords.add(password)
                    } else {
                        val scopes = query.split(",").toTypedArray()
                        scopes.sort()
                        var match = true
                        run sorting@{
                            scopes.forEach { scope ->
                                match = sScopes.contains(scope)
                                if (!match)
                                    return@sorting
                            }
                        }
                        if (match)
                            passwords.add(password)
                    }
                } else
                    passwords.add(password)
            }
        }
        return passwords
    }

    /**
     * Method to create the [PasswordTab] layout
     */
    @Composable
    override fun createTab() {
        passwordTab.createPasswordTab(selectedItem())
    }

}