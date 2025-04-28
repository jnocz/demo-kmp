/**
 * FavoriteItemDBManager
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package repository.favorites

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import co.touchlab.kermit.Logger
import database.favorites.model.DBFavoriteItem
import database.favorites.model.toFavoriteItem
import datasource.favorite.FavoritesDataSource
import datasource.search_result.SearchResultDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import screen.favorites.list.FavoriteItem

class FavoritesRepositoryImpl(
    private val favoritesDataSource: FavoritesDataSource,
    private val searchDataSource: SearchResultDataSource
) : FavoritesRepository {

    private val PAGE_SIZE: Int = 10

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observePagingDataFavoriteItems(
    ): Flow<PagingData<FavoriteItem>> {

        return Pager(
            config = PagingConfig(
                PAGE_SIZE
            ), pagingSourceFactory = { favoritesDataSource.observeFavoritesById() }
        ).flow.mapLatest { pagingData -> pagingData.map { it.toFavoriteItem() } }
    }

    override fun observeFavorites(): Flow<List<DBFavoriteItem>?> =
        favoritesDataSource.observeFavorites()

    override suspend fun getFavorites() = favoritesDataSource.getFavorites()

    override suspend fun addLinkToFavorites(link: String): DBFavoriteItem? {
        searchDataSource.setSearchItemWithLinkAsFavorite(link)
        val favoriteItem = favoritesDataSource.saveLinkToFavorites(link)
        return favoriteItem
    }

    override suspend fun removeLinkFromFavorites(link: String): Boolean {
        try {
            searchDataSource.setItemWithLinkAsNotFavorite(link)
            return favoritesDataSource.removeLinkFromFavorites(link)
        } catch (e: Exception) {
            Logger.e(e) { e.message.toString() }
            return false
        }
    }

    override suspend fun existsFavoriteItemWithLink(link: String): Boolean {
        return favoritesDataSource.getFavoriteByLink(link) != null
    }

}