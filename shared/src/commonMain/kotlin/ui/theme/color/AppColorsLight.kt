package ui.theme.color

import androidx.compose.material3.lightColorScheme

internal fun lightColorScheme(): AppColors = AppColors(
    ripple = AppColorPalette.blue,
    splash = AppColorPalette.blue,
    materialColors = lightColorScheme(
        primary = AppColorPalette.blue,
        primaryContainer = AppColorPalette.white,
        onPrimaryContainer = AppColorPalette.black,
        secondaryContainer = AppColorPalette.veryLightBlue,
        error = AppColorPalette.darkRed,
        background = AppColorPalette.white,
    ),
)
