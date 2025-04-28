package datasource.favorite

import androidx.paging.PagingSource
import database.favorites.model.DBFavoriteItem
import screen.favorites.list.FavoriteItem
import kotlinx.coroutines.flow.Flow

interface FavoritesDataSource {
    suspend fun getFavorites(): List<DBFavoriteItem>?
    fun observeFavorites(): Flow<List<DBFavoriteItem>?>
    fun observeFavoritesById(): PagingSource<Int, DBFavoriteItem>

    suspend fun getFavoriteByLink(link: String): DBFavoriteItem?

    suspend fun deleteFavorite(item: FavoriteItem)

    suspend fun saveFavoriteItemToDb(item: DBFavoriteItem)
    suspend fun toggleFavoriteItemInDb(item: DBFavoriteItem)

    suspend fun saveLinkToFavorites(link: String): DBFavoriteItem?
    suspend fun removeLinkFromFavorites(link: String): Boolean


}