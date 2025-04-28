package ui.theme.color

import androidx.compose.ui.graphics.Color
import utils.toColorInt

/** All available colors for app. */
object AppColorPalette {
    val blue = Color(0xFF415F91)
    val lightBlue = Color(0xFF6783F7)
    val veryLightBlue = Color(0xFFDAE2F9)
    val gray = Color(0xFF9E9E9E)
    val lightGrayInt = "#E7E7E7".toColorInt()
    val lightGray = Color(lightGrayInt)
    val veryLightGray = Color(0xFFEFEFEF)
    val black: Color = Color.Black
    val green: Color = Color(0xFF44CC44)
    val green1: Color = Color(0xFFFF4CAF50)
    val darkGreen: Color = Color(0xFF388E3C)
    val whiteColorInt: Int = "#ffffff".toColorInt()
    val white: Color = Color(whiteColorInt)
    val darkRed: Color = Color(0xFFCC4444)
    val violetColorInt: Int = "#3B0851".toColorInt()
    val violet: Color = Color(violetColorInt)
    val red: Color = Color(0xFFF40022)
    val darkRedHeart = Color(0xFFD32F2F)

}