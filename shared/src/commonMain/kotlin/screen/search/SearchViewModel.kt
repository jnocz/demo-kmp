/**
 * SearchViewModel.kt
 * 22.10.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package screen.search

import androidx.annotation.MainThread
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import bazarsearchmultiplatform.shared.generated.resources.Res
import co.touchlab.kermit.Logger
import database.history.model.DBHistoryItem
import datasource.local.LocalDataSource
import di.AppCoroutineDispatchers
import engine.domain.SearchResult
import engine.domain.SearchedPage
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import repository.history.HistoryRepository
import repository.search.SearchRepository
import screen.base.BaseViewModel
import utils.ResultState


data class SearchUiState(
    val loadingResult: utils.Result<Any> = utils.Result(state = ResultState.INIT),
    val loaderResourceUrl: String = Res.getUri(DrawableConstants.LOADER_TURTLE.value),
    val errorMessages: List<String> = listOf(),
    val currentSearchedPage: SearchedPage? = null
)

open class SearchViewModel(
    private val historyRepository: HistoryRepository,
    private val searchResultRepository: SearchRepository,
    private var dispatchers: AppCoroutineDispatchers,
    private val localDataSource: LocalDataSource,
) : BaseViewModel() {

    private val _searchResults = MutableStateFlow<PagingData<SearchResult>>(PagingData.empty())
    val searchResults: StateFlow<PagingData<SearchResult>> = _searchResults.asStateFlow()

    var uiState by mutableStateOf(SearchUiState())
        private set

    private var initializeCalled = false

    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true
        viewModelScope.launch(dispatchers.network) {
            observeSearchedResults()
        }
    }

    /* ****************************************************************************************** */
    /* ON SEARCH QUERY                                                                          */
    /* ****************************************************************************************** */

    fun onSearchQueryChanged(query: String) {
    }

    private suspend fun observeSearchedResults() {
        val searchParams = createSearchParams()

        searchResultRepository.observeSearchResultsDataSourceWithProps(
            searchParams.location,
            searchParams.priceFrom,
            searchParams.priceTo
        )
            .cachedIn(viewModelScope).collectLatest { pagingData ->
                _searchResults.value = pagingData
            }
    }

    private suspend fun createSearchParams(): SearchParams {
        var location = ""
        var priceFrom: Int? = null
        var priceTo: Int? = null

        try {

            val securedPriceFrom: Int? = try {
                localDataSource.priceFrom.firstOrNull()?.toInt()
            } catch (e: Exception) {
                Logger.e(e) { e.message.toString() }
                null
            }

            val securedPriceTo: Int? = try {
                localDataSource.priceTo.firstOrNull()?.toInt()
            } catch (e: Exception) {
                Logger.e(e) { e.message.toString() }
                null
            }

            priceFrom = securedPriceFrom
            priceTo = securedPriceTo

            location =
                localDataSource.location.firstOrNull()?.lowercase() ?: ""
        } catch (e: Exception) {
            Logger.e(e) { e.message.toString() }
        }

        return SearchParams(location, priceFrom, priceTo)
    }

    data class SearchParams(
        var location: String = "",
        var priceFrom: Int? = null,
        var priceTo: Int? = null
    )

    private fun saveSearchQueryToHistory(query: String) {
        val result = viewModelScope.async(dispatchers.database) {
            historyRepository.saveHistoryItemToDb(DBHistoryItem(phrase = query))
        }

        result.invokeOnCompletion {
            Logger.e(it) { "" }
        }
    }


    /* ****************************************************************************************** */
    /* TOGGLE FAVORITES                                                                           */
    /* ****************************************************************************************** */

    fun toggleFavoriteItem(displayedSearchResult: SearchResult) {
        viewModelScope.launch(dispatchers.database) {
            searchResultRepository.setFavoriteStatusForItem(
                displayedSearchResult.link,
                displayedSearchResult.isFavorite
            )
        }
    }
}

enum class DrawableConstants(val value: String) {
    NO_SEARCH_ITEM_AVAILABLE("drawable/no_search_item_available.gif"),
    LOADER_TURTLE("drawable/loader_turtle_loading.gif"),
    LOADER_SEARCH_IMM("drawable/loader_search_imm.gif"),
    LOADER_SEO_SEARCH("drawable/loader_seo_search.gif"),
    LOADER_SEARCHING_DATA("drawable/loader_searching_data.gif"),
    LOADER_HAPPY_WHALE("drawable/loader_5_happy_whale.gif"),
    SEARCH_INIT("drawable/search_init.gif"),
    SEARCH_HISTORY("drawable/search_history_1.gif"),
    FAVOURITES_LIKE_ANIMATION("drawable/favourites_like_animation.gif"),
    DONATION("drawable/donation.gif")
}