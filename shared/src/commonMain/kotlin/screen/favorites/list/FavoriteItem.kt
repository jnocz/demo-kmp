/*
 * DisplayedFavoriteItem.kt
 * 29.03.2019
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2019. All rights reserved.
 *
 */
package screen.favorites.list

data class FavoriteItem(
    var id: Long = 0,
    var link: String = "",
    var json: String = ""
)