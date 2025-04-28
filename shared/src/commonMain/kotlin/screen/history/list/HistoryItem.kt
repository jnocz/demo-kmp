/*
 * DisplayedHistoryItem.kt.kt
 * 17.09.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package screen.history.list

import kotlinx.datetime.LocalDateTime

data class HistoryItem(
    var id: Long = 0,
    var phrase: String = "",
    var date: LocalDateTime
)
