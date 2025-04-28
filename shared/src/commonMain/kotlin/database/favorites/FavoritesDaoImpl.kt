/*
 * FavoriteItemDbDataSource.kt
 * 13.09.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package database.favorites

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import database.favorites.model.DBFavoriteItem
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoritesDaoImpl : FavoritesDao {

    companion object {
        const val TABLE_NAME = "favorite_item"
        const val ALL_FAVORITES_QUERY = "select * from $TABLE_NAME"
    }

    @Query(ALL_FAVORITES_QUERY)
    override suspend fun getFavoriteItems(): List<DBFavoriteItem>

    @Query("$ALL_FAVORITES_QUERY ORDER BY id DESC")
    override fun observeFavoritesById(): PagingSource<Int, DBFavoriteItem>

    @Query(ALL_FAVORITES_QUERY)
    override fun observeFavoriteItems(): PagingSource<Int, DBFavoriteItem>

    @Query(ALL_FAVORITES_QUERY)
    override fun observeFavorites(): Flow<List<DBFavoriteItem>>

    @Query("$ALL_FAVORITES_QUERY ORDER BY id ASC")
    override fun getFavoritesByIdAsc(): Flow<List<DBFavoriteItem>>

    @Query("$ALL_FAVORITES_QUERY where id = :id")
    override suspend fun getFavoriteItem(id: Long): DBFavoriteItem

    @Query("$ALL_FAVORITES_QUERY where link = :link")
    override suspend fun getFavoriteByLink(link: String): DBFavoriteItem?

    @Query("delete from $TABLE_NAME")
    override suspend fun deleteAll(): Int
}