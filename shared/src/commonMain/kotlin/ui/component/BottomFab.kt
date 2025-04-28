package ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ui.theme.color.AppColorPalette

@Composable
fun BottomFab(fabText: String? = null, icon: DrawableResource, onClick: () -> Unit, bottomPadding: Int) {
    FloatingActionButton(
        containerColor = AppColorPalette.violet,
        modifier = Modifier.padding(bottom = bottomPadding.dp).size(137.dp, 37.dp),
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(20.dp),
    ) {

        Row(modifier = Modifier.padding(all = 1.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(modifier = Modifier.size(21.dp),
                colorFilter = ColorFilter.tint(Color.White),
                painter = painterResource(icon),
                contentDescription = null
            )
            if (fabText != null) {
                Text(
                    modifier = Modifier.padding(top = 2.dp, start = 15.dp),
                    fontSize = 17.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    text = fabText,
                )
            }
        }

    }
}