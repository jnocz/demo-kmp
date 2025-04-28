package repository.favorites

import androidx.paging.PagingData
import database.favorites.model.DBFavoriteItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import screen.favorites.list.FavoriteItem

class FakeFavoritesRepository : FavoritesRepository {

    var demoObjects: MutableList<DBFavoriteItem> = mutableListOf()

    override fun observePagingDataFavoriteItems(): Flow<PagingData<FavoriteItem>> {
        return flow { PagingData.empty<FavoriteItem>() }
    }

    override fun observeFavorites(): Flow<List<DBFavoriteItem>?> {
        return flow { PagingData.empty<List<DBFavoriteItem>>() }
    }

    override suspend fun getFavorites(): List<DBFavoriteItem> {
        return demoObjects
    }

    override suspend fun addLinkToFavorites(link: String): DBFavoriteItem? {
        val dbItem = DBFavoriteItem(0, link)
        demoObjects.add(dbItem)
        return dbItem
    }

    override suspend fun removeLinkFromFavorites(link: String): Boolean {
        val item = demoObjects.firstOrNull { it.link == link }
        demoObjects.remove(item)
        return true
    }

    override suspend fun existsFavoriteItemWithLink(link: String): Boolean {
        val item = demoObjects.firstOrNull { it.link == link }
        return item != null
    }

}