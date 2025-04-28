/*
 * HistoryItemView.kt
 * 07.06.2024
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2024. All rights reserved.
 *
 */

package ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.ic_search_black_24dp
import screen.history.list.HistoryItem
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import utils.formatPrettyDateTime

@Composable
fun HistoryItemView(
    historyItem: HistoryItem,
    onClickListener: (String) -> Unit
) {
    Box(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .clickable { onClickListener(historyItem.phrase) }) {

        Row(
            modifier = Modifier
                .padding(all = 10.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(Res.drawable.ic_search_black_24dp),
                contentDescription = ""
            )
            Spacer(
                Modifier.width(20.dp)
            )
            Column {
                if (historyItem.phrase.isNotBlank()) {
                    Text(
                        text = historyItem.phrase,
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 1
                    )
                }
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = historyItem.date.formatPrettyDateTime(),
                    modifier = Modifier.padding(end = 5.dp),
                    lineHeight = 15.sp,
                    maxLines = 3,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }
    }
}

@Preview
@Composable
fun HistoryItemViewPreview() {
//    BazarSearchTheme {
//        HistoryItemView(
//            HistoryItem(
//                id = 1,
//                phrase = "Hledaný výraz",
//                date = LocalDateTime.now()
//            ), {})
//    }
}
