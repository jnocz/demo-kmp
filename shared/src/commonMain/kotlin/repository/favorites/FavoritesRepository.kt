package repository.favorites

import androidx.paging.PagingData
import database.favorites.model.DBFavoriteItem
import kotlinx.coroutines.flow.Flow
import screen.favorites.list.FavoriteItem

interface FavoritesRepository {

    fun observePagingDataFavoriteItems(
    ): Flow<PagingData<FavoriteItem>>

    fun observeFavorites(): Flow<List<DBFavoriteItem>?>

    suspend fun getFavorites(): List<DBFavoriteItem>?

    suspend fun addLinkToFavorites(link: String): DBFavoriteItem?

    suspend fun removeLinkFromFavorites(link: String): Boolean

    suspend fun existsFavoriteItemWithLink(link: String): Boolean

}