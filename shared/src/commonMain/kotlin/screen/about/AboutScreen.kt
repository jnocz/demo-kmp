/*
 * AboutScreen.kt
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2024. All rights reserved.
 *
 */

package screen.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.about
import bazarsearchmultiplatform.shared.generated.resources.app_url_google_play
import bazarsearchmultiplatform.shared.generated.resources.author_email
import bazarsearchmultiplatform.shared.generated.resources.author_name
import bazarsearchmultiplatform.shared.generated.resources.evaluate_on_play_store
import bazarsearchmultiplatform.shared.generated.resources.ic_email_black_24dp
import bazarsearchmultiplatform.shared.generated.resources.ic_face_black_24dp
import bazarsearchmultiplatform.shared.generated.resources.ic_heart_red_small
import bazarsearchmultiplatform.shared.generated.resources.ic_info_outline_black_24dp
import bazarsearchmultiplatform.shared.generated.resources.ic_redeem_black_24dp
import bazarsearchmultiplatform.shared.generated.resources.ic_share
import bazarsearchmultiplatform.shared.generated.resources.ic_shield_lock_outline
import bazarsearchmultiplatform.shared.generated.resources.privacy_policy
import bazarsearchmultiplatform.shared.generated.resources.privacy_policy_url
import bazarsearchmultiplatform.shared.generated.resources.share
import getScreenWidth
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import ui.component.SimpleAutoLinkText
import ui.component.AboutScreenEvaluateAutoLinkText
import ui.component.CustomTextLink
import ui.component.ShareDialog
import ui.theme.BazarSearchTheme
import utils.OpenBrowserUrl


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AboutScreen(
    modifier: Modifier = Modifier, state: SheetState, onDismissRequest: () -> Unit
) {

    ModalBottomSheet(sheetState = state,
        sheetMaxWidth = getScreenWidth(),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        onDismissRequest = { onDismissRequest() }, content = {
            AboutScreenContent(modifier)
        })
}

@Composable
fun AboutScreenContent(
    modifier: Modifier = Modifier,
    viewModel: AboutViewModel = koinViewModel()
) {

    val appUrlGooglePlay = stringResource(Res.string.app_url_google_play)
    val privacyPolicyUrl = stringResource(Res.string.privacy_policy_url)

    val openShareDialog = remember { mutableStateOf(false) }

    val openPrivacyPolicy = remember { mutableStateOf(false) }

    if (openShareDialog.value) {
        ShareDialog(appUrlGooglePlay)
        openShareDialog.value = false
    }

    if (openPrivacyPolicy.value) {
        OpenBrowserUrl(privacyPolicyUrl)
        openPrivacyPolicy.value = false
    }

    Column(modifier.padding(bottom = 20.dp, start = 10.dp, end = 10.dp)) {
        Text(
            modifier = modifier.padding(start = 10.dp),
            style = MaterialTheme.typography.titleLarge,
            text = stringResource(Res.string.about)
        )
        Spacer(modifier = modifier.padding(top = 10.dp))
        AboutRowItem(
            icon = Res.drawable.ic_info_outline_black_24dp,
            content = { Text(text = viewModel.getVersionNumber()) }
        )
        AboutRowItem(icon = Res.drawable.ic_face_black_24dp,
            content = { Text(text = stringResource(Res.string.author_name)) })

        AboutRowItem(
            icon = Res.drawable.ic_email_black_24dp,
            content = {
                SimpleAutoLinkText(Res.string.author_email)
            })

        AboutRowItem(
            icon = Res.drawable.ic_redeem_black_24dp,
            content = {
                AboutScreenEvaluateAutoLinkText(Res.string.evaluate_on_play_store)
            }
        )

        AboutRowItem(icon = Res.drawable.ic_share,
            content = {
                CustomTextLink(
                    modifier,
                    Res.string.share
                ) {
                    openShareDialog.value = true
                }
            })

        AboutRowItem(
            icon = Res.drawable.ic_shield_lock_outline
        ) {
            CustomTextLink(
                modifier,
                Res.string.privacy_policy
            ) {
                openPrivacyPolicy.value = true
            }
        }
    }
}


@Composable
fun AboutRowItem(
    modifier: Modifier = Modifier, icon: DrawableResource, content: @Composable() () -> Unit
) {
    Row(
        modifier = modifier
            .padding(all = 5.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon), contentDescription = ""
        )
        Spacer(
            Modifier.width(20.dp)
        )

        content.invoke()
    }
    Spacer(modifier = modifier.height(10.dp))
}


@Preview
@Composable
fun AboutRowItemPreview() {
    BazarSearchTheme {
        AboutRowItem(Modifier, Res.drawable.ic_heart_red_small, content = {
            Text(
                text = stringResource(
                    Res.string.about
                )
            )
        })
    }
}


@Composable
@Preview
fun AboutScreenContentPreview() {
    BazarSearchTheme {

    }
}