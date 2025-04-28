/*
 * SettingsScreen.kt
 * 10.06.2024
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2024. All rights reserved.
 *
 */

package screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.ic_search_black_24dp
import bazarsearchmultiplatform.shared.generated.resources.location
import bazarsearchmultiplatform.shared.generated.resources.price_from
import bazarsearchmultiplatform.shared.generated.resources.price_to
import bazarsearchmultiplatform.shared.generated.resources.search_annonce_text
import bazarsearchmultiplatform.shared.generated.resources.search_antik_olomouc_text
import bazarsearchmultiplatform.shared.generated.resources.search_avizo_text
import bazarsearchmultiplatform.shared.generated.resources.search_bazarcz_text
import bazarsearchmultiplatform.shared.generated.resources.search_bazarsk_text
import bazarsearchmultiplatform.shared.generated.resources.search_bazos_pl_text
import bazarsearchmultiplatform.shared.generated.resources.search_bazos_sk_text
import bazarsearchmultiplatform.shared.generated.resources.search_bazos_text
import bazarsearchmultiplatform.shared.generated.resources.search_ebay_kleinanzeigen_de_text
import bazarsearchmultiplatform.shared.generated.resources.search_hudebni_bazar_text
import bazarsearchmultiplatform.shared.generated.resources.search_hyperinzerce_text
import bazarsearchmultiplatform.shared.generated.resources.search_mimibazar_text
import bazarsearchmultiplatform.shared.generated.resources.search_olx_pl_text
import bazarsearchmultiplatform.shared.generated.resources.search_poshmark_ca_text
import bazarsearchmultiplatform.shared.generated.resources.search_poshmark_com_text
import bazarsearchmultiplatform.shared.generated.resources.search_sbazar_text
import bazarsearchmultiplatform.shared.generated.resources.search_sprzedajemy_pl_text
import bazarsearchmultiplatform.shared.generated.resources.search_subito_it_text
import bazarsearchmultiplatform.shared.generated.resources.search_viva_street_co_uk_text
import bazarsearchmultiplatform.shared.generated.resources.settings
import bazarsearchmultiplatform.shared.generated.resources.settings_ca
import bazarsearchmultiplatform.shared.generated.resources.settings_czech_republic
import bazarsearchmultiplatform.shared.generated.resources.settings_de
import bazarsearchmultiplatform.shared.generated.resources.settings_it
import bazarsearchmultiplatform.shared.generated.resources.settings_poland
import bazarsearchmultiplatform.shared.generated.resources.settings_slovak_republic
import bazarsearchmultiplatform.shared.generated.resources.settings_uk
import bazarsearchmultiplatform.shared.generated.resources.settings_us
import getScreenWidth
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import ui.component.AlertDialogWithTextField
import ui.theme.BazarSearchTheme


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
    state: SheetState,
    onDismissRequest: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ModalBottomSheet(
        sheetState = state,
        sheetMaxWidth = getScreenWidth(),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Row(Modifier.padding(horizontal = 20.dp)) {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = stringResource(Res.string.settings)
                )
            }
            Spacer(modifier.height(10.dp))

            val openLocationDialog = remember { mutableStateOf(false) }
            val openPriceFromDialog = remember { mutableStateOf(false) }
            val openPriceToDialog = remember { mutableStateOf(false) }

            val locationsKeyboardOptions = KeyboardOptions.Default.copy(
                autoCorrectEnabled = false, imeAction = ImeAction.Next
            )

            SettingsTextField(
                uiState.location,
                modifier.padding(horizontal = 20.dp),
                locationsKeyboardOptions,
                Res.string.location,
                Icons.Filled.LocationOn,
            ) { openLocationDialog.value = true }

            if (openLocationDialog.value) {
                AlertDialogWithTextField(
                    uiState.location,
                    { openLocationDialog.value = false },
                    {
                        viewModel.updateLocation(it)
                        openLocationDialog.value = false
                    },
                    stringResource(Res.string.location),
                    Icons.Filled.LocationOn,
                    50,
                    locationsKeyboardOptions
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                val priceKeyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                )

                SettingsTextField(
                    uiState.priceFrom,
                    Modifier.weight(1f),
                    priceKeyboardOptions,
                    Res.string.price_from,
                    Icons.Filled.Money
                ) {
                    openPriceFromDialog.value = true
                }
                if (openPriceFromDialog.value) {
                    AlertDialogWithTextField(
                        uiState.priceFrom,
                        { openPriceFromDialog.value = false },
                        {
                            viewModel.updatePriceFrom(it)
                            openPriceFromDialog.value = false
                        },
                        stringResource(Res.string.price_from),
                        Icons.Filled.Money,
                        9,
                        priceKeyboardOptions
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                SettingsTextField(
                    uiState.priceTo,
                    Modifier.weight(1f),
                    priceKeyboardOptions,
                    Res.string.price_to,
                    Icons.Filled.Money
                ) {
                    openPriceToDialog.value = true
                }

                if (openPriceToDialog.value) {
                    AlertDialogWithTextField(
                        uiState.priceTo,
                        { openPriceToDialog.value = false },
                        {
                            viewModel.updatePriceTo(it)
                            openPriceToDialog.value = false
                        },
                        stringResource(Res.string.price_to),
                        Icons.Filled.Money,
                        9,
                        priceKeyboardOptions
                    )
                }

            }

            SettingsCard {
                SettingsCardTitle(textResId = Res.string.settings_czech_republic)

                SettingItem(
                    switchChecked = uiState.searchBazosCZ,
                    message = stringResource(Res.string.search_bazos_text),
                    onCheckedChange = {
                        viewModel.updateSearchBazosCZ(it)
                    })
                SettingItem(
                    switchChecked = uiState.searchSBazar,
                    message = stringResource(Res.string.search_sbazar_text),
                    onCheckedChange = {
                        viewModel.updateSearchSBazar(it)
                    })
                SettingItem(
                    switchChecked = uiState.searchBazarCz,
                    message = stringResource(Res.string.search_bazarcz_text),
                    onCheckedChange = {
                        viewModel.updateSearchBazarCZ(it)
                    })
                SettingItem(
                    switchChecked = uiState.searchHudebniBazar,
                    message = stringResource(Res.string.search_hudebni_bazar_text),
                    onCheckedChange = {
                        viewModel.updateSearchHudebniBazar(it)
                    })
                SettingItem(
                    switchChecked = uiState.searchAnnonce,
                    message = stringResource(Res.string.search_annonce_text),
                    onCheckedChange = {
                        viewModel.updateSearchAnnonce(it)
                    })
                SettingItem(
                    switchChecked = uiState.searchHyperinzerce,
                    message = stringResource(Res.string.search_hyperinzerce_text),
                    onCheckedChange = {
                        viewModel.updateSearchHyperinzerce(it)
                    })

                SettingItem(
                    switchChecked = uiState.searchAntikOlomouc,
                    message = stringResource(Res.string.search_antik_olomouc_text),
                    onCheckedChange = {
                        viewModel.updateSearchAntikOlomouc(it)
                    })


                SettingItem(
                    switchChecked = uiState.searchAvizo,
                    message = stringResource(Res.string.search_avizo_text),
                    onCheckedChange = {
                        viewModel.updateSearchAvizo(it)
                    })

                SettingItem(
                    switchChecked = uiState.searchMimiBazar,
                    message = stringResource(Res.string.search_mimibazar_text),
                    onCheckedChange = {
                        viewModel.updateSearchMimiBazar(it)
                    })
            }

            SettingsCard {

                SettingsCardTitle(textResId = Res.string.settings_slovak_republic)

                SettingItem(
                    switchChecked = uiState.searchBazarSK,
                    message = stringResource(Res.string.search_bazarsk_text),
                    onCheckedChange = {
                        viewModel.updateSearchBazarSK(it)
                    })

                SettingItem(
                    switchChecked = uiState.searchBazosSK,
                    message = stringResource(Res.string.search_bazos_sk_text),
                    onCheckedChange = {
                        viewModel.updateSearchBazosSK(it)
                    })
            }


            SettingsCard {

                SettingsCardTitle(textResId = Res.string.settings_poland)

                SettingItem(
                    switchChecked = uiState.searchBazosPL,
                    message = stringResource(Res.string.search_bazos_pl_text),
                    onCheckedChange = {
                        viewModel.updateSearchBazosPL(it)
                    })

                SettingItem(
                    switchChecked = uiState.searchOlxPl,
                    message = stringResource(Res.string.search_olx_pl_text),
                    onCheckedChange = {
                        viewModel.updateSearchOlxPl(it)
                    })


                SettingItem(
                    switchChecked = uiState.searchSprzedajemyPl,
                    message = stringResource(Res.string.search_sprzedajemy_pl_text),
                    onCheckedChange = {
                        viewModel.updateSearchSprzedajemyPl(it)
                    })
            }

            SettingsCard {

                SettingsCardTitle(textResId = Res.string.settings_de)

                SettingItem(
                    switchChecked = uiState.searchEbayKleinanzeigenDe,
                    message = stringResource(Res.string.search_ebay_kleinanzeigen_de_text),
                    onCheckedChange = {
                        viewModel.updateSearchEbayKleinanzeigenDe(it)
                    })

            }

            SettingsCard {
                SettingsCardTitle(textResId = Res.string.settings_uk)

                SettingItem(
                    switchChecked = uiState.searchVivaStreetCoUk,
                    message = stringResource(Res.string.search_viva_street_co_uk_text),
                    onCheckedChange = {
                        viewModel.updateSearchVivaStreetCoUk(it)
                    })

            }

            SettingsCard {
                SettingsCardTitle(textResId = Res.string.settings_it)
                SettingItem(
                    switchChecked = uiState.searchSubitoIt,
                    message = stringResource(Res.string.search_subito_it_text),
                    onCheckedChange = {
                        viewModel.updateSearchSubitoIt(it)
                    })

            }

            SettingsCard {
                SettingsCardTitle(textResId = Res.string.settings_us)
                SettingItem(
                    switchChecked = uiState.searchPoshmarkCom,
                    message = stringResource(Res.string.search_poshmark_com_text),
                    onCheckedChange = {
                        viewModel.updateSearchPoshmarkCom(it)
                    })

            }
            SettingsCard {
                SettingsCardTitle(textResId = Res.string.settings_ca)

                SettingItem(
                    switchChecked = uiState.searchPoshmarkCa,
                    message = stringResource(Res.string.search_poshmark_ca_text),
                    onCheckedChange = {
                        viewModel.updateSearchPoshmarkCa(it)
                    })
            }

            Spacer(modifier = modifier.padding(bottom = 20.dp))
        }
    }
}

@Composable
private fun SettingsCardTitle(modifier: Modifier = Modifier, textResId: StringResource) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier.padding(start = 10.dp, top = 20.dp),
            style = MaterialTheme.typography.titleMedium,
            text = stringResource(textResId)
        )
    }

}

@Composable
private fun SettingsCard(modifier: Modifier = Modifier, content: @Composable (ColumnScope.() -> Unit)) {
    Card(
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        //border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
        modifier = modifier.padding(15.dp)
    ) {
        content()
    }
}


@Composable
fun SettingsTextField(
    initFieldValue: String?,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions,
    hintTextId: StringResource,
    leadingIcon: ImageVector,
    onOpenAlertDialogClick: () -> Unit
) {
    val fieldHint = stringResource(hintTextId)
    val fieldText by rememberSaveable { mutableStateOf(initFieldValue) }

    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            disabledBorderColor = Color.Black,
            disabledTextColor = Color.Black,
        ),
        enabled = false,
        modifier = modifier.clickable { onOpenAlertDialogClick() },
        keyboardOptions = keyboardOptions,
        leadingIcon = {
            Icon(imageVector = leadingIcon, contentDescription = "")
        },
        value = initFieldValue ?: "",
        onValueChange = {},
        label = { Text(fieldHint) },
        singleLine = true
    )
}

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    message: String = "",
    switchChecked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?
) {
    Row(
        modifier = modifier
            .padding(all = 10.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_search_black_24dp), contentDescription = ""
        )
        Spacer(
            Modifier.width(20.dp)
        )
        Text(text = message)
        Spacer(
            Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        Switch(
            checked = switchChecked,
            onCheckedChange = onCheckedChange,
            enabled = true,
            thumbContent = if (switchChecked) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                null
            }
        )
    }
}

@Preview
@Composable
fun SettingItemPreview() {
    BazarSearchTheme {
        val text = stringResource(Res.string.search_hudebni_bazar_text)
        SettingItem(message = text, switchChecked = true, onCheckedChange = {})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SettingsScreenPreview() {
    BazarSearchTheme {
        val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        SettingsScreen(state = state, viewModel = koinViewModel(), onDismissRequest = {})
    }
}