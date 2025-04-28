package ui.theme.typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.sfpro_bold
import bazarsearchmultiplatform.shared.generated.resources.sfpro_medium
import bazarsearchmultiplatform.shared.generated.resources.sfpro_regular
import org.jetbrains.compose.resources.Font

object FontFamilies {
    @Composable
    fun sfPro() = FontFamily(
        Font(
            resource = Res.font.sfpro_bold,
            weight = FontWeight.Bold,
            style = FontStyle.Normal,
        ),
        Font(
            resource = Res.font.sfpro_medium,
            weight = FontWeight.Medium,
            style = FontStyle.Normal,
        ),
        Font(
            resource = Res.font.sfpro_regular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal,
        ),
    )
}
