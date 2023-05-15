@file:Suppress("UNCHECKED_CAST")

package layouts.components.sections.lists

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * **List** is the super class where all the views inheriting are enabled to contain a list of items
 *
 * @author Tecknobit - N7ghtm4r3
 * **/
abstract class List {

    /**
     * **itemsList** -> items list to create the view
     */
    protected var itemsList: MutableList<Any> = mutableStateListOf()

    /**
     * **selectedItem** -> the selected item to work on
     */
    protected lateinit var selectedItem: MutableState<Any?>

    /**
     * **loadFirstItem** -> whether load the first of the list
     */
    protected var loadFirstItem: Boolean = true

    /**
     * Method to load the [itemsList]
     *
     * @param content: the layout content to set for the list
     */
    @Composable
    protected fun loadList(content: LazyListScope.() -> Unit) {
        Spacer(Modifier.height(16.dp))
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = content
        )
    }

    /**
     * Method to create a tab layout
     */
    @Composable
    abstract fun createTab()

    /**
     * Method to get the [selectedItem]. No-any params required
     *
     * @return the selected item as [T]
     */
    fun <T> selectedItem(): T {
        return selectedItem.value as T
    }

}