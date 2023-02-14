package layouts.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.tecknobit.glider.helpers.GliderLauncher
import com.tecknobit.glider.records.Password
import helpers.backgroundColor
import helpers.cBackgroundColor
import helpers.primaryColor
import layouts.components.PasswordItem
import layouts.parents.RequestManager

/**
 * This is the layout for the mainScreen screen
 *
 * @author Tecknobit - N7ghtm4r3
 * @see RequestManager
 * **/
class MainScreen : RequestManager() {

    private lateinit var scaffoldState: ScaffoldState

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    @Preview
    fun createMainScreen() {
        scaffoldState = rememberScaffoldState()
        Box(
            modifier = Modifier.fillMaxSize().background(cBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.width(1440.dp).height(920.dp),
                elevation = 5.dp,
                shape = RoundedCornerShape(25.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Scaffold(
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        scaffoldState = scaffoldState,
                        backgroundColor = primaryColor,
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
                        Column {
                            Box(modifier = Modifier.fillMaxSize()) {
                                val listState = rememberLazyListState()
                                val calculatedOffset = remember { mutableStateOf(0f) }
                                val myData = listOf(PasswordItem(), PasswordItem(), PasswordItem(), PasswordItem())
                                LazyColumn(
                                    modifier = Modifier.height(1000.dp).pointerInput(Unit) {
                                        detectDragGestures { change, dragAmount ->
                                            change.consume()
                                            calculatedOffset.value = dragAmount.x
                                        }
                                    },
                                    state = listState,
                                    contentPadding = PaddingValues(16.dp),
                                ) {
                                    item {
                                        Row(
                                            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                                .padding(vertical = 25.dp),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Passwords",
                                                style = MaterialTheme.typography.h3
                                            )
                                        }
                                    }
                                    items(myData) { item ->
                                        Row(
                                            Modifier.animateItemPlacement()
                                                .graphicsLayer(translationY = calculatedOffset.value)
                                        ) {
                                            item.passwordItem(
                                                Password(
                                                    null, "tat", ArrayList<String>(listOf("List")), "gaga",
                                                    Password.Status.DELETED
                                                ), scaffoldState
                                            )
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
                        }
                    }
                    Spacer(Modifier.width(50.dp))
                    Column(
                        modifier = Modifier.weight(1f).fillMaxHeight().background(backgroundColor)
                    ) {

                    }
                }
            }
        }
    }

    override fun <T> setRequestPayload(operation: GliderLauncher.Operation?, vararg params: T) {
        super.setRequestPayload(operation, *params)
    }

}