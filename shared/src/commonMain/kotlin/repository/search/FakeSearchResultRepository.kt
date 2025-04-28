package repository.search

import androidx.paging.PagingData
import engine.domain.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSearchResultRepository() : SearchRepository {

    override fun observeSearchResultsDataSourceWithProps(
        location: String,
        priceFrom: Int?,
        priceTo: Int?
    ): Flow<PagingData<SearchResult>> {
        return flow { PagingData.empty<SearchResult>() }
    }

    override suspend fun setFavoriteStatusForItem(link: String, isFavorite: Boolean): Boolean {
        return false
    }
}