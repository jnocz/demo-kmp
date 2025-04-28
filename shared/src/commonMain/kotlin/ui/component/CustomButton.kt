package ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ui.theme.color.AppColorPalette

@Composable
fun CustomButton(
    text: String? = null,
    icon: DrawableResource? = null,
    onClick: () -> Unit,
    color: Color,
    bottomPadding: Int = 0
) {

    Button(
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = Modifier.padding(bottom = bottomPadding.dp).height(39.dp).wrapContentWidth()
            .defaultMinSize(162.dp),
        shape = RoundedCornerShape(20.dp), content = {

            Row(
                modifier = Modifier.padding(all = 1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon != null) {
                    Image(
                        modifier = Modifier.size(21.dp),
                        colorFilter = ColorFilter.tint(AppColorPalette.white),
                        painter = painterResource(icon),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                }
                if (!text.isNullOrBlank()) {
                    Text(
                        text = text,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        fontSize = 17.sp,
                        color = AppColorPalette.white,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        },
        onClick = {
            onClick()
        })
}


@Composable
fun SmallCustomButton(
    text: String? = null,
    icon: DrawableResource? = null,
    onClick: () -> Unit,
    color: Color,
    bottomPadding: Int = 0
) {

    Button(
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = Modifier.padding(bottom = bottomPadding.dp).wrapContentWidth(),
        shape = RoundedCornerShape(20.dp), content = {
            Row(
                modifier = Modifier.padding(all = 1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon != null) {
                    Image(
                        modifier = Modifier.size(19.dp),
                        colorFilter = ColorFilter.tint(AppColorPalette.white),
                        painter = painterResource(icon),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                }
                if (!text.isNullOrBlank()) {
                    Text(
                        text = text,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        fontSize = 15.sp,
                        color = AppColorPalette.white,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        },
        onClick = {
            onClick()
        })
}