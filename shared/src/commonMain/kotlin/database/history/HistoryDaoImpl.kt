/*
 * HistoryDbDataSource.kt
 * 17.09.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package database.history

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import database.history.model.DBHistoryItem
import kotlinx.coroutines.flow.Flow


@Dao
interface HistoryDaoImpl : HistoryDao {

    companion object {
        const val TABLE_NAME = "history_item"
        const val ALL_HISTORY_ITEMS_QUERY = "select * from $TABLE_NAME"
    }

    @Query("$ALL_HISTORY_ITEMS_QUERY order by date DESC")
    override suspend fun getHistoryItems(): List<DBHistoryItem>

    @Query("$ALL_HISTORY_ITEMS_QUERY order by date DESC")
    override fun observeHistoryItems(): Flow<List<DBHistoryItem>>

    @Query("$ALL_HISTORY_ITEMS_QUERY order by date DESC")
    override fun getHistoryItemsByDate(): PagingSource<Int, DBHistoryItem>

    @Query("$ALL_HISTORY_ITEMS_QUERY where id = :id")
    override suspend fun getHistoryItem(id: Long): DBHistoryItem

    @Query("$ALL_HISTORY_ITEMS_QUERY where phrase = :phrase")
    override suspend fun getHistoryByPhrase(phrase: String): DBHistoryItem?

    @Query("delete from $TABLE_NAME")
    override suspend fun deleteAll(): Int


}