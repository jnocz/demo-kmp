/*
 * SearchResultRepository.kt
 * 02.11.2023
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2023. All rights reserved.
 *
 */
package repository.search

import androidx.paging.PagingData
import datasource.favorite.FavoritesDataSource
import datasource.search_result.SearchResultDataSource
import engine.domain.SearchResult
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(
    private val searchResultDataSource: SearchResultDataSource,
    private val favoritesDataSource: FavoritesDataSource
) : SearchRepository {

    override fun observeSearchResultsDataSourceWithProps(
        location: String, priceFrom: Int?, priceTo: Int?
    ): Flow<PagingData<SearchResult>> {
        return searchResultDataSource.observeSearchResultsDataSourceWithProps(
            location,
            priceFrom,
            priceTo
        )
    }

    override suspend fun setFavoriteStatusForItem(link: String, isFavorite: Boolean): Boolean {
        if (isFavorite) {
            searchResultDataSource.setSearchItemWithLinkAsFavorite(link)
            val savedItem = favoritesDataSource.saveLinkToFavorites(link)
            return savedItem != null
        } else {
            searchResultDataSource.setItemWithLinkAsNotFavorite(link)
            val removed = favoritesDataSource.removeLinkFromFavorites(link)
            return removed
        }
    }

}