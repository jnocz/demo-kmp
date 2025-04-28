package datasource.search_result

import androidx.paging.PagingData
import database.search.model.DBSearchResult
import engine.domain.SearchResult
import kotlinx.coroutines.flow.Flow

interface SearchResultDataSource {
    fun observeSearchResultsDataSourceWithProps(
        location: String, priceFrom: Int?, priceTo: Int?
    ): Flow<PagingData<SearchResult>>

    fun deleteAllSearchResultsInDB()
    suspend fun deleteAll()
    suspend fun insertAll(parsedResults: MutableList<DBSearchResult>)
    suspend fun deleteAllAndInsert(parsedResults: List<DBSearchResult>)
    suspend fun setSearchItemWithLinkAsFavorite(link: String)
    suspend fun setItemWithLinkAsNotFavorite(link: String)
}