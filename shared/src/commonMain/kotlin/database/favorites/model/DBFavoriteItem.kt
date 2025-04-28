/*
 * FavoriteItem.kt
 * 13.09.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package database.favorites.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import screen.favorites.list.FavoriteItem


@Entity(tableName = "favorite_item")
data class DBFavoriteItem(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "link")
    var link: String = "",

    @ColumnInfo(name = "json")
    var json: String = ""
)

fun DBFavoriteItem.toFavoriteItem(): FavoriteItem =
    FavoriteItem(id = id, json = json, link = link)


