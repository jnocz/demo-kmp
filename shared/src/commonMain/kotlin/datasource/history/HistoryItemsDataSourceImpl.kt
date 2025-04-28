package datasource.history

import androidx.paging.PagingSource
import co.touchlab.kermit.Logger
import database.AppDatabase
import database.history.model.DBHistoryItem
import database.history.model.toHistoryItem
import di.AppCoroutineDispatchers
import screen.history.list.HistoryItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HistoryItemsDataSourceImpl(
    private val db: AppDatabase,
    private var dispatchers: AppCoroutineDispatchers,
) : HistoryItemsDataSource {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeHistoryItems(
    ): Flow<List<HistoryItem>> =
        db.historyDao().observeHistoryItems().mapLatest { list ->
            list.map { item -> item.toHistoryItem() }
        }

    override fun observeHistoryItemsById(): PagingSource<Int, DBHistoryItem> {
        return db.historyDao().getHistoryItemsByDate()
    }

    override suspend fun saveHistoryItemToDb(historyItem: DBHistoryItem): Unit =
        withContext(dispatchers.database) {
            try {
                val oldValue = db.historyDao().getHistoryByPhrase(historyItem.phrase)
                if (oldValue == null) {
                    db.historyDao().insert(historyItem)
                } else {
                    oldValue.date =
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    db.historyDao().update(oldValue)
                }
            } catch (e: Exception) {
                Logger.e(e) { e.message.toString() }
            }
        }

}