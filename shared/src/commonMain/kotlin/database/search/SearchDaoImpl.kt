/*
 * SearchResultDbDataSource.kt
 * 01.10.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package database.search

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import database.search.model.DBSearchResult
import kotlinx.coroutines.flow.Flow


@Dao
interface SearchDaoImpl : SearchDao {

    companion object {
        const val TABLE_NAME = "search_result"
        const val ALL_SEARCH_RESULTS_QUERY = "SELECT * FROM $TABLE_NAME"
        const val LOCATION_PROP =
            "(location LIKE '%' || :location || '%' OR search_location LIKE '%' || :location || '%') "
        const val PRICE_PROP = "(parsed_price BETWEEN :priceFrom AND :priceTo)"
    }

    @Query("$ALL_SEARCH_RESULTS_QUERY order by inserted_date DESC")
    override suspend fun getSearchResults(): List<DBSearchResult>

    @Query("$ALL_SEARCH_RESULTS_QUERY ORDER BY searched_page ASC")
    override fun getSearchResultsByDate(): Flow<List<DBSearchResult>>

    @Query("$ALL_SEARCH_RESULTS_QUERY ORDER BY relevant DESC")
    override fun getSearchResultsDataSourceByRelevance(): PagingSource<Int, DBSearchResult>

    @Query("$ALL_SEARCH_RESULTS_QUERY ORDER BY relevant DESC")
    override fun getSearchResultsFlowByRelevance(): Flow<List<DBSearchResult>>

    @Query("$ALL_SEARCH_RESULTS_QUERY ORDER BY relevant DESC")
    override suspend fun getSearchResultsByRelevance(): List<DBSearchResult>

    @Query("$ALL_SEARCH_RESULTS_QUERY WHERE $LOCATION_PROP ORDER BY relevant DESC")
    override fun getSearchResultsDataSourceByRelevanceAndLocation(location: String): PagingSource<Int, DBSearchResult>

    @Query("$ALL_SEARCH_RESULTS_QUERY WHERE $LOCATION_PROP ORDER BY relevant DESC")
    override fun getSearchResultsFlowByRelevanceAndLocation(location: String): Flow<List<DBSearchResult>>

    @Query("$ALL_SEARCH_RESULTS_QUERY WHERE $LOCATION_PROP ORDER BY relevant DESC")
    override suspend fun getSearchResultsByRelevanceAndLocation(location: String): List<DBSearchResult>

    @Query("$ALL_SEARCH_RESULTS_QUERY WHERE $LOCATION_PROP AND $PRICE_PROP ORDER BY relevant DESC")
    override fun getSearchResultsDataSourceByRelevanceAndLocationAndPrices(
        location: String,
        priceFrom: Int,
        priceTo: Int
    ): PagingSource<Int, DBSearchResult>


    @Query("$ALL_SEARCH_RESULTS_QUERY WHERE $LOCATION_PROP AND $PRICE_PROP ORDER BY relevant DESC")
    override fun getSearchResultsFlowByRelevanceAndLocationAndPrices(
        location: String,
        priceFrom: Int,
        priceTo: Int
    ): Flow<List<DBSearchResult>>

    @Query("$ALL_SEARCH_RESULTS_QUERY WHERE $LOCATION_PROP AND $PRICE_PROP ORDER BY relevant DESC")
    override suspend fun getSearchResultsByRelevanceAndLocationAndPrices(
        location: String,
        priceFrom: Int,
        priceTo: Int
    ): List<DBSearchResult>


    @Query("$ALL_SEARCH_RESULTS_QUERY WHERE $PRICE_PROP ORDER BY relevant DESC")
    override fun getSearchResultsDataSourceByRelevanceAndPrices(
        priceFrom: Int,
        priceTo: Int
    ): PagingSource<Int, DBSearchResult>

    @Query("$ALL_SEARCH_RESULTS_QUERY WHERE $PRICE_PROP ORDER BY relevant DESC")
    override fun getSearchResultsFlowByRelevanceAndPrices(
        priceFrom: Int,
        priceTo: Int
    ): Flow<List<DBSearchResult>>

    @Query("$ALL_SEARCH_RESULTS_QUERY WHERE $PRICE_PROP ORDER BY relevant DESC")
    override suspend fun getSearchResultsByRelevanceAndPrices(
        priceFrom: Int,
        priceTo: Int
    ): List<DBSearchResult>

    @Query("$ALL_SEARCH_RESULTS_QUERY where id = :id")
    override suspend fun getSearchResult(id: Long): DBSearchResult?

    @Query("$ALL_SEARCH_RESULTS_QUERY where link = :link")
    override suspend fun getSearchResultByLink(link: String): DBSearchResult?

    @Query("delete from $TABLE_NAME")
    override suspend fun deleteAll(): Int

    @Query("UPDATE $TABLE_NAME SET is_favorite = :isFavorite where id = :id")
    override suspend fun updateIsFavorite(id: Long, isFavorite: Boolean): Int



}