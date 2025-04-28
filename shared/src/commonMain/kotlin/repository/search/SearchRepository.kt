package repository.search

import androidx.paging.PagingData
import engine.domain.SearchResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun observeSearchResultsDataSourceWithProps(
        location: String, priceFrom: Int?, priceTo: Int?
    ): Flow<PagingData<SearchResult>>

    suspend fun setFavoriteStatusForItem(link: String, isFavorite: Boolean): Boolean

}