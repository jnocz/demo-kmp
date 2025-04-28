/*
 * SearchTextField.kt
 * 31.05.2024
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2024. All rights reserved.
 *
 */

package ui.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.ic_arrow_forward_black_24dp
import bazarsearchmultiplatform.shared.generated.resources.ic_search_black_24dp
import bazarsearchmultiplatform.shared.generated.resources.search
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.color.AppColorPalette


@Composable
fun SearchTextField(
    initPhrase: String? = "",
    onSearchIconClick: (String) -> (Unit),
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val placeholder = stringResource(Res.string.search)
    var textValue by rememberSaveable { mutableStateOf(initPhrase) }
    OutlinedTextField(
        value = textValue ?: "",
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = {
            searchClicked(onSearchIconClick, textValue, keyboardController)
        }),
        placeholder = { Text(text = placeholder, color = AppColorPalette.gray) },
        shape = RoundedCornerShape(100),
        //textStyle = inputTextStyle,
        leadingIcon = {
            SearchTextIcon(Res.drawable.ic_search_black_24dp)
        },
        trailingIcon = {
            SearchTextIcon(
                Res.drawable.ic_arrow_forward_black_24dp,
                {
                    searchClicked(onSearchIconClick, textValue, keyboardController)
                })
        },
        onValueChange = {
            textValue = it
        },
        colors = OutlinedTextFieldDefaults.colors(
//            unfocusedLabelColor = AppColorPalette.gray,
//            unfocusedTextColor = AppColorPalette.gray
        ),
        textStyle = TextStyle(
            color = AppColorPalette.black,
            fontSize = 19.sp,
            fontWeight = FontWeight.Normal,
            textDecoration = TextDecoration.None
        ),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
    )
}

private fun searchClicked(
    onSearchIconClick: (String) -> Unit,
    textValue: String?,
    keyboardController: SoftwareKeyboardController?
) {
    onSearchIconClick(textValue ?: "")
    keyboardController?.hide()
}


@Composable
private fun SearchTextIcon(resource: DrawableResource, onClick: () -> Unit = {}) {
    IconButton(onClick = { onClick() }) {
        Icon(
            painter = painterResource(resource),
            contentDescription = "",
            modifier = Modifier
                .size(25.dp)
                .padding(0.dp)
        )
    }
}

@Preview
@Composable
fun SearchTextFieldPreview() {
    SearchTextField("") {}
}