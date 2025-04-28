package ui.theme.typography

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ui.theme.typography.FontFamilies.sfPro

@Immutable
data class AppTypographies(
    val headline: TextStyle,
    val subline: TextStyle,
    val body: TextStyle,
    val copy: TextStyle,
    val material: Typography,
)

@Composable
fun appTypographies(): AppTypographies {
    val sfPro = sfPro()
    val materialTypography = Typography()
    return AppTypographies(
        headline = TextStyle(
            fontFamily = sfPro,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            fontSize = 34.sp,
            lineHeight = 24.sp,
            color = MaterialTheme.colorScheme.primaryContainer,
        ),
        subline = TextStyle(
            fontFamily = sfPro,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            fontSize = 25.sp,
            lineHeight = 18.sp,
            color = MaterialTheme.colorScheme.primaryContainer,
        ),
        body = TextStyle(
            fontFamily = sfPro,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
            fontSize = 12.sp,
            lineHeight = 14.sp,
        ),
        copy = TextStyle(
            fontFamily = sfPro,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            fontSize = 10.sp,
            lineHeight = 12.sp,
        ),
        material = Typography(
            displayLarge = materialTypography.displayLarge.copy(fontFamily = sfPro),
            displayMedium = materialTypography.displayMedium.copy(fontFamily = sfPro),
            displaySmall = materialTypography.displaySmall.copy(fontFamily = sfPro),
            headlineLarge = materialTypography.headlineLarge.copy(fontFamily = sfPro),
            headlineMedium = materialTypography.headlineMedium.copy(fontFamily = sfPro),
            headlineSmall = materialTypography.headlineSmall.copy(fontFamily = sfPro),
            titleLarge = materialTypography.titleLarge.copy(fontFamily = sfPro),
            titleMedium = materialTypography.titleMedium.copy(fontFamily = sfPro),
            titleSmall = materialTypography.titleSmall.copy(fontFamily = sfPro),
            bodyLarge = materialTypography.bodyLarge.copy(fontFamily = sfPro),
            bodyMedium = materialTypography.bodyMedium.copy(fontFamily = sfPro),
            bodySmall = materialTypography.bodySmall.copy(fontFamily = sfPro),
            labelLarge = materialTypography.labelLarge.copy(fontFamily = sfPro),
            labelMedium = materialTypography.labelMedium.copy(fontFamily = sfPro),
            labelSmall = materialTypography.labelSmall.copy(fontFamily = sfPro),
        ),
    )
}
