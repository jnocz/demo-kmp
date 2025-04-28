package datasource.favorite

import androidx.paging.PagingSource
import co.touchlab.kermit.Logger
import database.AppDatabase
import di.AppCoroutineDispatchers
import database.favorites.model.DBFavoriteItem
import database.search.model.toSearchResult
import engine.domain.toDBFavoriteItem
import screen.favorites.list.FavoriteItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FavoritesDataSourceImpl(
    private val db: AppDatabase,
    private var dispatchers: AppCoroutineDispatchers,
) : FavoritesDataSource {

    override fun observeFavoritesById(): PagingSource<Int, DBFavoriteItem> =
        db.favoritesDao().observeFavoritesById()

    override fun observeFavorites(): Flow<List<DBFavoriteItem>?> =
        db.favoritesDao().observeFavorites()

    override suspend fun getFavorites(): List<DBFavoriteItem> = withContext(dispatchers.database) {
        withContext(dispatchers.database) {
            db.favoritesDao().getFavoriteItems()
        }
    }

    override suspend fun getFavoriteByLink(link: String): DBFavoriteItem? =
        withContext(dispatchers.database) {
            db.favoritesDao().getFavoriteByLink(link)
        }

    override suspend fun deleteFavorite(item: FavoriteItem) = withContext(dispatchers.database) {
        try {
            val favorite = db.favoritesDao().getFavoriteByLink(item.link)
            if (favorite != null) {
                val deletedCount = db.favoritesDao().delete(favorite)
                if (deletedCount > 0) {
                    Logger.d { "row deleted from favorites table" }
                } else {
                    Logger.e { "Cannot delete from favorites table" }
                }
            }
        } catch (e: Exception) {
            Logger.e(e) { "Cannot delete from favorites table" }
        }
    }

    override suspend fun saveFavoriteItemToDb(item: DBFavoriteItem) =
        withContext(dispatchers.database) {
            try {
                val currentDbItem = db.favoritesDao().getFavoriteByLink(item.link)
                if (currentDbItem == null) {
                    db.favoritesDao().insert(item)
                }
            } catch (e: Exception) {
                Logger.e(e) { e.printStackTrace().toString() }
            }
        }


    override suspend fun toggleFavoriteItemInDb(item: DBFavoriteItem): Unit =
        withContext(dispatchers.database) {
            try {
                val currentDbItem = db.favoritesDao().getFavoriteByLink(item.link)
                if (currentDbItem == null) {
                    db.favoritesDao().insert(item)
                } else {
                    db.favoritesDao().delete(currentDbItem)
                }
            } catch (e: Exception) {
                Logger.e(e) { "Cannot insert or delete favorite item!" }
            }
        }

    override suspend fun saveLinkToFavorites(link: String): DBFavoriteItem? =
        withContext(dispatchers.database) {
            try {
                val dbSearchResult = db.searchDao().getSearchResultByLink(link)
                var dbFavoriteItem = db.favoritesDao().getFavoriteByLink(link)

                if (dbFavoriteItem == null) {
                    val newDBItem = dbSearchResult?.toSearchResult()?.toDBFavoriteItem()
                    if (newDBItem != null) {
                        db.favoritesDao().insert(newDBItem)
                        dbFavoriteItem = db.favoritesDao().getFavoriteByLink(link)
                    }
                }
                return@withContext dbFavoriteItem
            } catch (e: Exception) {
                Logger.e(e) { e.message.toString() }
                return@withContext null
            }
        }


    override suspend fun removeLinkFromFavorites(link: String): Boolean =
        withContext(dispatchers.database) {
            try {
                var status = false
                val dbFavoriteItem = db.favoritesDao().getFavoriteByLink(link)
                if (dbFavoriteItem != null) {
                    val itemsRemoved = db.favoritesDao().delete(dbFavoriteItem)
                    status = itemsRemoved > 0
                }
                return@withContext status
            } catch (e: Exception) {
                Logger.e(e) { e.message.toString() }
                return@withContext false
            }
        }

}