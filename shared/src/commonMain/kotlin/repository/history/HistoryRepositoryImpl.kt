/*
 * HistoryDBManager.kt
 * 17.09.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package repository.history

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import database.history.model.DBHistoryItem
import database.history.model.toHistoryItem
import datasource.history.HistoryItemsDataSource
import screen.history.list.HistoryItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

open class HistoryRepositoryImpl(
    private var historyItemsDataSource: HistoryItemsDataSource
) : HistoryRepository {

    private val PAGE_SIZE: Int = 10

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeDbPagingDataHistoryItems(
    ): Flow<PagingData<HistoryItem>> {

        return Pager(
            config = PagingConfig(
                PAGE_SIZE
            ), pagingSourceFactory = { historyItemsDataSource.observeHistoryItemsById() }
        ).flow.mapLatest { pagingData -> pagingData.map { it.toHistoryItem() } }
    }

    override fun observeDbHistoryItems(
    ): Flow<List<HistoryItem>> =
        historyItemsDataSource.observeHistoryItems()


    override suspend fun saveHistoryItemToDb(historyItem: DBHistoryItem) =
        historyItemsDataSource.saveHistoryItemToDb(historyItem)

}