/*
 * ListItems.kt
 * 02.07.2024
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2024. All rights reserved.
 *
 */

package ui.component.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag

@Composable
fun ErrorItem(
    modifier: Modifier = Modifier,
    message: String = "",
    onClickRetry: () -> (Unit)
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        TextButton(
            modifier = modifier.testTag("textButton"),
            content = { Text(text = message, modifier = Modifier) },
            onClick = { onClickRetry() })
    }
}

@Composable
fun LoadingItem(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(color = Color.Blue)
    }
}