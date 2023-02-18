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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import helpers.backgroundColor

abstract class List {

    protected lateinit var itemsList: MutableList<Any>
    protected lateinit var selectedItem: MutableState<Any>

    @Composable
    protected fun loadList(content: LazyListScope.() -> Unit) {
        selectedItem = remember { mutableStateOf(itemsList[0]) }
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

    fun <T> selectedItem(): T {
        return selectedItem.value as T
    }

}