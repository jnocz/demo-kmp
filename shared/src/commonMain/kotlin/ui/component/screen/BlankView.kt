/*
 * BlankView.kt
 * 02.07.2024
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2024. All rights reserved.
 *
 */

package ui.component.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.component.GifImage
import ui.theme.BazarSearchTheme

@Composable
fun BlankView(
     titleId: StringResource,
     imageUri: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                style = MaterialTheme.typography.titleMedium,
                text = stringResource(titleId),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(0.dp))
            GifImage(imageUri = imageUri)
        }
    }
}

@Composable
@Preview
fun BlankViewPreview() {
    BazarSearchTheme {
//        BlankView(
//            Res.string.no_records_found,
//            Res.drawable.no_search_item_available
//        )
    }
}