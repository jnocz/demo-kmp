import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import ui.component.list.ErrorItem
import ui.theme.BazarSearchTheme
import kotlin.test.Test

class ErrorItemUITest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun myTest() = runComposeUiTest {

        setContent {
            BazarSearchTheme {
                ErrorItem(
                    Modifier,
                    message = "Hello",
                    onClickRetry = {}
                )
            }
        }

        onNodeWithTag("textButton").assertTextEquals("Hello")
    }
}