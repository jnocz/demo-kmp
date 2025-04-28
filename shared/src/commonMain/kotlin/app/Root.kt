package app

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import co.touchlab.kermit.Logger
import isDebug
import navigation.Navigation
import org.koin.compose.KoinContext
import ui.theme.BazarSearchTheme
import utils.logging.NoopWriter

@Composable
fun Root(
    modifier: Modifier = Modifier,
) {
    KoinContext {
        if(!isDebug) {
            Logger.setLogWriters(NoopWriter(true))
        }

        val navHostController = rememberNavController()
        BazarSearchTheme {
            Navigation(
                navHostController = navHostController,
                modifier = modifier.background(color = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    }
}
