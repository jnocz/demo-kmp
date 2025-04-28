package repository.history

import androidx.paging.PagingData
import database.history.model.DBHistoryItem
import kotlinx.coroutines.flow.Flow
import screen.history.list.HistoryItem

interface HistoryRepository {

    fun observeDbPagingDataHistoryItems(
    ): Flow<PagingData<HistoryItem>>

    fun observeDbHistoryItems(
    ): Flow<List<HistoryItem>>

    suspend fun saveHistoryItemToDb(historyItem: DBHistoryItem)
}