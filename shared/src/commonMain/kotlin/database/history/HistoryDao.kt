/*
 * HistoryDataSource.kt
 * 17.09.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package database.history

import androidx.paging.PagingSource
import database.BaseDao
import database.history.model.DBHistoryItem
import kotlinx.coroutines.flow.Flow


interface HistoryDao : BaseDao<DBHistoryItem> {

    suspend fun getHistoryItems(): List<DBHistoryItem>

    fun observeHistoryItems(): Flow<List<DBHistoryItem>>

    fun getHistoryItemsByDate(): PagingSource<Int, DBHistoryItem>
    
    suspend fun getHistoryItem(id: Long): DBHistoryItem?

    suspend fun getHistoryByPhrase(phrase: String): DBHistoryItem?

    suspend fun deleteAll(): Int
}