/*
 * SearchResult.kt
 * 01.10.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package database.search.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import database.convertes.RoomTypeConverters
import database.convertes.SearchedPageConverter
import engine.domain.SearchResult
import engine.domain.SearchedPage
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


@Entity(tableName = "search_result")
data class DBSearchResult(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "link")
    var link: String = "",

    @ColumnInfo(name = "price")
    var price: String? = null,

    @ColumnInfo(name = "parsed_price")
    var parsedPrice: Int? = null,

    @ColumnInfo(name = "image_src")
    var imageSrc: String? = null,

    @ColumnInfo(name = "location")
    var location: String? = null,

    @ColumnInfo(name = "search_location")
    var searchLocation: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "inserted_date_on_bazaar")
    var insertedDateOnBazaar: String? = null,

    @ColumnInfo(name = "relevant")
    var relevant: Boolean = false,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,

    @ColumnInfo(name = "searched_page")
    @TypeConverters(SearchedPageConverter::class)
    var searchedPage: SearchedPage = SearchedPage.BAZOS_CZ,

    @TypeConverters(RoomTypeConverters::class)
    @ColumnInfo(name = "inserted_date")
    var insertedDate: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
)

fun DBSearchResult.toSearchResult(): SearchResult =
    SearchResult(
        id,
        name,
        link,
        price,
        imageSrc,
        location,
        description,
        insertedDateOnBazaar,
        searchedPage,
        isFavorite
    )


