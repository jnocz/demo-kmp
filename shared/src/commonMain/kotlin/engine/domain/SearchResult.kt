/*
 * SearchResult.kt
 * 10.09.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package engine.domain


import database.favorites.model.DBFavoriteItem
import database.search.model.DBSearchResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import utils.notBlankOrNull
import utils.unaccent

@Serializable
data class SearchResult(
    @SerialName("id")
    var id: Long = 0,
    @SerialName("name")
    var name: String? = null,
    @SerialName("link")
    var link: String = "",
    @SerialName("price")
    var price: String? = null,
    @SerialName("imageSrc")
    var imageSrc: String? = null,
    @SerialName("location")
    var location: String? = null,
    @SerialName("description")
    var description: String? = null,
    @SerialName("insertedDateOnBazaar")
    var insertedDateOnBazaar: String? = null,
    @SerialName("searchedPage")
    var searchedPage: SearchedPage = SearchedPage.BAZOS_CZ,
    @SerialName("favorite")
    var isFavorite: Boolean = false
)

fun SearchResult.toDBFavoriteItem(): DBFavoriteItem {
    this.isFavorite = true
    return DBFavoriteItem(
        link = this.link,
        json = Json.encodeToString(this)
    )
}


data class ParsedSearchResult(
    var id: Long = 0,
    var name: String? = null,
    var link: String = "",
    var price: String? = null,
    var imageSrc: String? = null,
    var location: String? = null,
    var description: String? = null,
    var insertedDateOnBazaar: String? = null,
    var searchedPage: SearchedPage = SearchedPage.BAZOS_CZ
)

fun ParsedSearchResult.toDbFavoriteItem(): DBFavoriteItem {
    return DBFavoriteItem(
        link = this.link,
        json = Json.encodeToString(this)
    )
}

fun ParsedSearchResult.toDBSearchResult(): DBSearchResult {
    return DBSearchResult(
        name = name,
        link = link,
        price = price,
        parsedPrice = parsePrice(price),
        imageSrc = imageSrc,
        location = location,
        searchLocation = location?.lowercase()?.unaccent(),
        description = description,
        insertedDateOnBazaar = insertedDateOnBazaar,
        searchedPage = searchedPage
    )
}


fun ParsedSearchResult.createGUISearchResult(): SearchResult {
    return SearchResult(
        id,
        name,
        link,
        price,
        imageSrc,
        location,
        description,
        insertedDateOnBazaar,
        searchedPage
    )
}

private fun parsePrice(price: String?): Int? = try {
    price?.notBlankOrNull()?.replace("[^0-9]+".toRegex(), "")?.trim()?.toInt()
} catch (e: Exception) {
    null
}


