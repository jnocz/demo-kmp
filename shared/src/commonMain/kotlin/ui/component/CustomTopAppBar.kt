package ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.ic_heart_red_small
import org.jetbrains.compose.resources.painterResource
import screen.about.AboutScreen
import screen.donate.DonateScreen
import screen.settings.SettingsScreen
import ui.theme.color.AppColorPalette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    navigationIcon: @Composable (() -> Unit),
    scrollBehavior: TopAppBarScrollBehavior
) {

    val collapsed = 22
    val expanded = 28

    val isCollapsed =
        remember { derivedStateOf { scrollBehavior.state.collapsedFraction > 0.5 } }

    val topAppBarTextSize =
        (collapsed + (expanded - collapsed) * (1 - scrollBehavior.state.collapsedFraction)).sp

    val topAppBarElementColor = if (isCollapsed.value) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    var openBottomSettingsScreen by rememberSaveable { mutableStateOf(false) }
    var openBottomDonateScreen by rememberSaveable { mutableStateOf(false) }
    var openBottomAboutScreen by rememberSaveable { mutableStateOf(false) }

    val settingsScreenSkipPartiallyExpanded by remember { mutableStateOf(false) }
    val settingsScreenModalState =
        rememberModalBottomSheetState(skipPartiallyExpanded = settingsScreenSkipPartiallyExpanded)


    val aboutScreenSkipPartiallyExpanded by remember { mutableStateOf(true) }
    val aboutScreenModalState =
        rememberModalBottomSheetState(skipPartiallyExpanded = aboutScreenSkipPartiallyExpanded)


    val donateScreenSkipPartiallyExpanded by remember { mutableStateOf(false) }
    val donateScreenModalState =
        rememberModalBottomSheetState(skipPartiallyExpanded = donateScreenSkipPartiallyExpanded)

    if (openBottomDonateScreen) {
        DonateScreen(state = donateScreenModalState,
            onDismissRequest = { openBottomDonateScreen = false })
    }

    if (openBottomSettingsScreen) {
        SettingsScreen(state = settingsScreenModalState,
            onDismissRequest = { openBottomSettingsScreen = false })
    }

    if (openBottomAboutScreen) {
        AboutScreen(state = aboutScreenModalState,
            onDismissRequest = { openBottomAboutScreen = false })
    }

    MediumTopAppBar(
        title = {
            Row {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = topAppBarTextSize,
                    fontWeight = FontWeight.Bold
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            scrolledContainerColor = backgroundColor,
            titleContentColor = Color.Black,
            navigationIconContentColor = topAppBarElementColor,
            actionIconContentColor = topAppBarElementColor,
        ), navigationIcon = navigationIcon, actions = {
            IconButton(onClick = { openBottomDonateScreen = !openBottomDonateScreen }) {
                Icon(
                    painter = painterResource(Res.drawable.ic_heart_red_small),
                    null,
                    tint = AppColorPalette.darkRedHeart
                )
            }
            IconButton(onClick = { openBottomSettingsScreen = !openBottomSettingsScreen }) {
                Icon(Icons.Filled.Settings, null)
            }
            IconButton(onClick = { openBottomAboutScreen = !openBottomAboutScreen }) {
                Icon(Icons.Outlined.Info, null)
            }
        }, scrollBehavior = scrollBehavior
    )
}