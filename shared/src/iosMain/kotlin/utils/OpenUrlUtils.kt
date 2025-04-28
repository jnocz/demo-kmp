package utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler

@Composable
actual fun OpenBrowserUrl(url: String) {
    val uriHandler = LocalUriHandler.current
    uriHandler.openUri(url)
}