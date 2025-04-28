package datasource.history

import androidx.paging.PagingSource
import database.history.model.DBHistoryItem
import screen.history.list.HistoryItem
import kotlinx.coroutines.flow.Flow

interface HistoryItemsDataSource {
    fun observeHistoryItems(): Flow<List<HistoryItem>>
    fun observeHistoryItemsById(): PagingSource<Int, DBHistoryItem>
    suspend fun saveHistoryItemToDb(historyItem: DBHistoryItem)
}