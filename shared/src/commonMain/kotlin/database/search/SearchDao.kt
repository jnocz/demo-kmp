/*
 * SearchResultDataSource.kt
 * 01.10.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package database.search

import androidx.paging.PagingSource
import database.BaseDao
import database.search.model.DBSearchResult
import kotlinx.coroutines.flow.Flow


interface SearchDao : BaseDao<DBSearchResult> {

    suspend fun getSearchResults(): List<DBSearchResult>

    suspend fun getSearchResult(id: Long): DBSearchResult?

    suspend fun getSearchResultByLink(link: String): DBSearchResult?

    fun getSearchResultsByDate(): Flow<List<DBSearchResult>>

    fun getSearchResultsDataSourceByRelevance(): PagingSource<Int, DBSearchResult>

    fun getSearchResultsFlowByRelevance(): Flow<List<DBSearchResult>>

    suspend fun getSearchResultsByRelevance(): List<DBSearchResult>

    fun getSearchResultsDataSourceByRelevanceAndLocation(location: String): PagingSource<Int, DBSearchResult>

    fun getSearchResultsDataSourceByRelevanceAndLocationAndPrices(
        location: String,
        priceFrom: Int,
        priceTo: Int
    ): PagingSource<Int, DBSearchResult>

    fun getSearchResultsFlowByRelevanceAndLocationAndPrices(
        location: String,
        priceFrom: Int,
        priceTo: Int
    ): Flow<List<DBSearchResult>>

    suspend fun getSearchResultsByRelevanceAndLocationAndPrices(
        location: String,
        priceFrom: Int,
        priceTo: Int
    ): List<DBSearchResult>

    fun getSearchResultsDataSourceByRelevanceAndPrices(
        priceFrom: Int,
        priceTo: Int
    ): PagingSource<Int, DBSearchResult>

    fun getSearchResultsFlowByRelevanceAndPrices(
        priceFrom: Int,
        priceTo: Int
    ): Flow<List<DBSearchResult>>

    suspend fun getSearchResultsByRelevanceAndPrices(
        priceFrom: Int,
        priceTo: Int
    ): List<DBSearchResult>

    fun getSearchResultsFlowByRelevanceAndLocation(location: String): Flow<List<DBSearchResult>>

    suspend fun getSearchResultsByRelevanceAndLocation(location: String): List<DBSearchResult>

    suspend fun updateIsFavorite(id: Long, isFavorite: Boolean): Int

    suspend fun deleteAll(): Int

}