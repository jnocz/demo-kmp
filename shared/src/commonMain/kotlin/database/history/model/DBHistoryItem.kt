/*
 * HistoryItem.kt
 * 17.09.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package database.history.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import database.convertes.RoomTypeConverters
import screen.history.list.HistoryItem
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


@Entity(tableName = "history_item")
data class DBHistoryItem(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "phrase")
    var phrase: String = "",

    @TypeConverters(RoomTypeConverters::class)
    @ColumnInfo(name = "date")
    var date: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
)


fun DBHistoryItem.toHistoryItem(): HistoryItem =
    HistoryItem(id = id, date = date, phrase = phrase)
