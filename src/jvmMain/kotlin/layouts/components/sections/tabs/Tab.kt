package layouts.components.sections.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import helpers.RequestManager
import helpers.backgroundColor
import helpers.primaryColor
import kotlinx.coroutines.CoroutineScope

/**
 * **Tab** is the super class where all the views inheriting are enabled to contain a data to create a tab for the item
 *
 * @author Tecknobit - N7ghtm4r3
 * @see RequestManager
 * **/
abstract class Tab : RequestManager() {

    /**
     * **scaffoldState** -> the scaffold state for the scaffold of the page
     */
    protected lateinit var scaffoldState: ScaffoldState

    /**
     * **coroutineScope** -> the coroutine scope to manage the coroutines of the [scaffoldState]
     */
    protected lateinit var coroutineScope: CoroutineScope

    /**
     * Method to create a tab
     *
     * @param content: the layout content to set for the tab
     */
    @Composable
    protected fun createTab(content: @Composable ColumnScope.() -> Unit) {
        coroutineScope = rememberCoroutineScope()
        scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = primaryColor,
            contentColor = primaryColor,
            snackbarHost = {
                SnackbarHost(it) { data ->
                    Snackbar(
                        backgroundColor = backgroundColor,
                        contentColor = primaryColor,
                        snackbarData = data
                    )
                }
            }
        ) {
            Divider(thickness = 1.dp, color = Color.White)
            Column(
                modifier = Modifier.wrapContentHeight().padding(top = 30.dp).verticalScroll(rememberScrollState()),
                content = content
            )
        }
    }

    /**
     * Method to create an empty list layout when the list of the items is empty
     *
     * @param message: the message to show
     */
    @Composable
    protected fun showEmptyListLayout(message: String) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message,
                color = Color.White,
                fontSize = 35.sp
            )
        }
    }

}