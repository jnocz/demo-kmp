import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import io.github.alexzhirkevich.cupertino.adaptive.Theme

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun determineTheme(): Theme

expect val isDebug: Boolean

@Composable
expect fun getScreenWidth(): Dp

@Composable
expect fun getScreenHeight(): Dp