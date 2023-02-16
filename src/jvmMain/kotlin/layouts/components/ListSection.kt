package layouts.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecknobit.glider.records.Password
import com.tecknobit.glider.records.Password.Status.ACTIVE
import com.tecknobit.glider.records.Password.Status.DELETED
import helpers.backgroundColor
import helpers.greenColor
import helpers.redColor

/**
 * This is the layout for the list section where there is the list of the passwords
 *
 * @author Tecknobit - N7ghtm4r3
 * **/
class ListSection {

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
     * **selectedPassword** -> the selected password
     */
    private lateinit var selectedPassword: MutableState<Password>

    /**
     * Method to create the [ListSection] view. No-any params required
     */
    @Composable
    fun listSection() {
        lSelected = remember { mutableStateOf(true) }
        rSelected = remember { mutableStateOf(false) }
        querySearch = remember { mutableStateOf("") }
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                GliderTextField(
                    modifier = Modifier.height(60.dp),
                    text = "Filter by tail or scopes",
                    value = querySearch.value,
                    onChange = {
                        querySearch.value = it
                    },
                    leadingIcon = Default.Search,
                    leadingOnClick = {

                    },
                    trailingIcon = Default.Clear,
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
                                if (!lSelected.value)
                                    lSelected.value = true
                                rSelected.value = false
                            }
                        ) {
                            Icon(
                                imageVector = Default.ViewList,
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
                                if (!rSelected.value)
                                    rSelected.value = true
                                lSelected.value = false
                            }
                        ) {
                            Icon(
                                imageVector = Default.Delete,
                                contentDescription = null,
                                tint = if (!rSelected.value) redColor else Color.White
                            )
                        }
                    }
                }
            }
            loadPasswordsList()
        }
    }

    /**
     * Method to load the passwords list. No-any params required
     */
    @Composable
    private fun loadPasswordsList() {
        // TODO: TO USE THE CORRECT LIST OF PASSWORDS TO FILTER
        val dPasswords = listOf(
            Password(null, "tat", ArrayList<String>(listOf("List")), "gag41414142141414141414142141424", ACTIVE),
            Password(null, "tat1", ArrayList<String>(listOf("List")), "gaga", ACTIVE),
            Password(null, "tat", ArrayList<String>(listOf("List")), "gaga", ACTIVE),
            Password(null, "tat1", ArrayList<String>(listOf("List")), "gaga", ACTIVE),
            Password(null, "tat", ArrayList<String>(listOf("List")), "gaga", DELETED),
            Password(null, "tat1", ArrayList<String>(listOf("List")), "gaga", DELETED),
            Password(null, "tat", ArrayList<String>(listOf("List")), "gaga", DELETED),
            Password(null, "tat1", ArrayList<String>(listOf("List")), "gaga", DELETED),
            Password(null, "tat", ArrayList<String>(listOf("List")), "gaga", ACTIVE),
            Password(null, "tat1", ArrayList<String>(listOf("List")), "gaga", ACTIVE),
            Password(null, "tat", ArrayList<String>(listOf("List")), "gaga", ACTIVE),
            Password(null, "tat1", ArrayList<String>(listOf("List")), "gaga", ACTIVE),
            Password(null, "tat", ArrayList<String>(listOf("List")), "gaga", DELETED),
            Password(null, "tat1", ArrayList<String>(listOf("List")), "gaga", DELETED),
            Password(null, "tat", ArrayList<String>(listOf("List")), "gaga", DELETED),
            Password(null, "tat1", ArrayList<String>(listOf("List")), "gaga", DELETED),
            Password(null, "tat", ArrayList<String>(listOf("List")), "gaga", ACTIVE),
            Password(null, "tat1", ArrayList<String>(listOf("List")), "gaga", ACTIVE),
            Password(null, "tat", ArrayList<String>(listOf("List")), "gaga", ACTIVE),
            Password(null, "tat1", ArrayList<String>(listOf("List")), "gaga", ACTIVE),
            Password(null, "tat", ArrayList<String>(listOf("List")), "gaga", DELETED),
            Password(null, "tat1", ArrayList<String>(listOf("List")), "gaga", DELETED),
            Password(null, "tat", ArrayList<String>(listOf("List", "Netflix")), "gaga", DELETED),
            Password(
                null,
                "tat1",
                ArrayList<String>(listOf("List1", "Youtube", "Netflix", "Gagag", "gagfaga")),
                "gaga",
                DELETED
            )
        )
        val passwords = filterPasswords(dPasswords)
        val listState = rememberLazyListState()
        selectedPassword = rememberSaveable { mutableStateOf(passwords[0]) }
        Spacer(Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.height(1000.dp),
            state = listState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(passwords) { password ->
                Card(
                    modifier = Modifier.fillMaxWidth().height(65.dp).clickable {
                        selectedPassword.value = password
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
                                text = "tail",
                                fontSize = 20.sp
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier.background(if (password.status == ACTIVE) greenColor else redColor)
                                    .fillMaxHeight().width(100.dp)
                            )
                        }
                    }
                }
            }
        }
        VerticalScrollbar(
            style = ScrollbarStyle(
                minimalHeight = LocalScrollbarStyle.current.minimalHeight,
                hoverColor = backgroundColor,
                unhoverColor = Color.Transparent,
                hoverDurationMillis = LocalScrollbarStyle.current.hoverDurationMillis,
                thickness = LocalScrollbarStyle.current.thickness,
                shape = LocalScrollbarStyle.current.shape
            ),
            modifier = Modifier.fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = listState
            )
        )
    }

    /**
     * Method to load the passwords list
     * @param currentPasswords: the current passwords list to filter
     * @return the list of the passwords filtered as [MutableList] of [Password]
     */
    private fun filterPasswords(currentPasswords: List<Password>): MutableList<Password> {
        val passwords = mutableListOf<Password>()
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

    fun selectedPassword(): Password {
        return selectedPassword.value
    }

}