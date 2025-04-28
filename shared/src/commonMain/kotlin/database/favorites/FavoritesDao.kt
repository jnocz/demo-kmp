/*
 * FavoriteDataSource.kt
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package database.favorites

import androidx.paging.PagingSource
import database.BaseDao
import database.favorites.model.DBFavoriteItem
import kotlinx.coroutines.flow.Flow


interface FavoritesDao : BaseDao<DBFavoriteItem> {

    suspend fun getFavoriteItems(): List<DBFavoriteItem>

    fun observeFavoriteItems(): PagingSource<Int, DBFavoriteItem>

    fun observeFavorites(): Flow<List<DBFavoriteItem>>

    suspend fun getFavoriteItem(id: Long): DBFavoriteItem?

    suspend fun getFavoriteByLink(link: String): DBFavoriteItem?

    fun observeFavoritesById(): PagingSource<Int, DBFavoriteItem>

    fun getFavoritesByIdAsc(): Flow<List<DBFavoriteItem>>

    suspend fun deleteAll(): Int
}