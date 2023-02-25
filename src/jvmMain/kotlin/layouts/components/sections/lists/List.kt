@file:Suppress("UNCHECKED_CAST")

package layouts.components.sections.lists

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import helpers.backgroundColor

/**
 * **List** is the super class where all the views inheriting are enabled to contain a list of items
 *
 * @author Tecknobit - N7ghtm4r3
 * **/
abstract class List {

    /**
     * **itemsList** -> items list to create the view
     */
    protected lateinit var itemsList: MutableList<Any>

    /**
     * **selectedItem** -> the selected item to work on
     */
    protected lateinit var selectedItem: MutableState<Any?>

    /**
     * Method to load the [itemsList]
     *
     * @param content: the layout content to set for the list
     */
    @Composable
    protected fun loadList(content: LazyListScope.() -> Unit) {
        selectedItem =
            if (itemsList.size > 0)
                remember { mutableStateOf(itemsList[0]) }
            else
                remember { mutableStateOf(null) }
        val listState = rememberLazyListState()
        Spacer(Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.height(1000.dp),
            state = listState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = content
        )
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
     * Method to get the [selectedItem]. No-any params required
     *
     * @return the selected item as [T]
     */
    fun <T> selectedItem(): T {
        return selectedItem.value as T
    }

}