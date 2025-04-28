@file:Suppress("MatchingDeclarationName")

package ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.toFontFamily
import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.sfpro_bold
import bazarsearchmultiplatform.shared.generated.resources.sfpro_medium
import bazarsearchmultiplatform.shared.generated.resources.sfpro_regular
import determineTheme
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTheme
import io.github.alexzhirkevich.cupertino.adaptive.CupertinoThemeSpec
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.adaptive.MaterialThemeSpec
import io.github.alexzhirkevich.cupertino.adaptive.Theme
import org.jetbrains.compose.resources.Font
import ui.composition.LocalAppColors
import ui.composition.LocalAppIsInDarkMode
import ui.composition.LocalAppTypographies
import ui.theme.color.AppColors
import ui.theme.color.appColors
import ui.theme.typography.AppTypographies
import ui.theme.typography.appTypographies

object App {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typographies: AppTypographies
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypographies.current

    val isInDarkMode: Boolean
        @Composable
        @ReadOnlyComposable
        get() = LocalAppIsInDarkMode.current
}


object Fonts {
    lateinit var bold: FontFamily
    lateinit var medium: FontFamily
    lateinit var regular: FontFamily
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun BazarSearchTheme(
    isInDarkMode: Boolean = false, //isSystemInDarkTheme(),
    colors: AppColors = appColors(isInDarkMode = isInDarkMode),
    theme: Theme = determineTheme(),
    typographies: AppTypographies = appTypographies(),
    content: @Composable () -> Unit,
) {

    Fonts.bold = Font(Res.font.sfpro_bold).toFontFamily()
    Fonts.medium = Font(Res.font.sfpro_medium).toFontFamily()
    Fonts.regular = Font(Res.font.sfpro_regular).toFontFamily()

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypographies provides typographies,
        LocalAppIsInDarkMode provides isInDarkMode,
    ) {

//        MaterialTheme(
//            colorScheme = colors.materialColors,
//            typography = typographies.material,
//            content = content,
//        )

//        {
//            MaterialTheme(
//                colorScheme = if (isInDarkMode) {
//                    androidx.compose.material3.darkColorScheme()
//                } else {
//                    androidx.compose.material3.lightColorScheme()
//                },
//                content = it
//            )
//        }
//        {
//            CupertinoTheme(
//                colorScheme = if (isInDarkMode) {
//                    darkColorScheme()
//                } else {
//                    lightColorScheme()
//                },
//                content = it
//            )
//
//        }
        AdaptiveTheme(
            target = theme,
            material = MaterialThemeSpec.Default(
                colorScheme = colors.materialColors,
                typography = typographies.material
            ),
            cupertino = CupertinoThemeSpec.Default(),
            content = content
        )

    }
}
