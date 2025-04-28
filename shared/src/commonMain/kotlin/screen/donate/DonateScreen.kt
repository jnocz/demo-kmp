/*
 * DonateScreen.kt
 * 11.06.2024
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2024. All rights reserved.
 *
 */

package screen.donate

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.donate
import bazarsearchmultiplatform.shared.generated.resources.donate_link
import bazarsearchmultiplatform.shared.generated.resources.donate_on_buy_me_a_coffee
import bazarsearchmultiplatform.shared.generated.resources.donate_title
import bazarsearchmultiplatform.shared.generated.resources.ic_heart_black
import bazarsearchmultiplatform.shared.generated.resources.ic_heart_red_small
import getScreenWidth
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import screen.search.DrawableConstants
import ui.component.GifImage
import ui.component.SimpleAutoLinkText
import ui.theme.BazarSearchTheme
import ui.theme.color.AppColorPalette


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DonateScreen(
    modifier: Modifier = Modifier,
    state: SheetState,
    onDismissRequest: () -> Unit
) {

    ModalBottomSheet(
        sheetState = state,
        sheetMaxWidth = getScreenWidth(),
        onDismissRequest = { onDismissRequest() },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        content = {
            DonateScreenContent(modifier)
        }
    )
}

@Composable
fun DonateScreenContent(modifier: Modifier = Modifier) {

    val donateLink = stringResource(Res.string.donate_link)
    val uriHandler = LocalUriHandler.current

    Text(
        modifier = modifier.padding(start = 15.dp),
        style = MaterialTheme.typography.titleLarge,
        text = stringResource(Res.string.donate_title)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(10.dp)
    ) {

        Row(
            modifier = modifier
                .padding(all = 5.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_heart_red_small),
                contentDescription = "", tint = AppColorPalette.red
            )
            Spacer(
                Modifier.width(20.dp)
            )

            SimpleAutoLinkText(Res.string.donate_on_buy_me_a_coffee)
        }

        GifImage(modifier.height(150.dp), Res.getUri(DrawableConstants.DONATION.value))
        DonateButton(
            icon = painterResource(Res.drawable.ic_heart_black),
            text = stringResource(Res.string.donate),
            onClick = {
                uriHandler.openUri(donateLink)
            }
        )
        Spacer(modifier = modifier.height(20.dp))
    }
}


@Composable
fun DonateButton(
    icon: Painter,
    text: String,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(5.dp))
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(width = 200.dp)
            .height(height = 40.dp),
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.width(18.dp),
                    painter = icon,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = AppColorPalette.white)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text, fontSize = 15.sp)
            }

        }
    )
}


@Composable
@Preview
fun DonateScreenPreview() {
    BazarSearchTheme {
        DonateScreenContent()

    }
}
