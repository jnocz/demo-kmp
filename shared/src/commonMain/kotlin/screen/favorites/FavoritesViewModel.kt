/**
 * FavoritesViewModel.kt
 * 17.10.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package screen.favorites

import androidx.annotation.MainThread
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import di.AppCoroutineDispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import repository.favorites.FavoritesRepository
import repository.search.SearchRepository
import screen.base.BaseViewModel
import screen.favorites.list.FavoriteItem

class FavoritesViewModel(
    private val dispatchers: AppCoroutineDispatchers,
    private val favoritesRepository: FavoritesRepository,
    private val searchRepository: SearchRepository
) : BaseViewModel() {

    private var initializeCalled = false

    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true
        refreshObservingFavoritesRepository()

    }

    private val _favoritesResults = MutableStateFlow<PagingData<FavoriteItem>>(PagingData.empty())
    val favoritesResults: StateFlow<PagingData<FavoriteItem>> = _favoritesResults.asStateFlow()

    private fun refreshObservingFavoritesRepository() {
        viewModelScope.launch(dispatchers.database) {
            favoritesRepository.observePagingDataFavoriteItems().distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _favoritesResults.value = it
                }
        }
    }

    fun removeFavoriteItemWithLink(link: String) {
        viewModelScope.launch(dispatchers.database) {
            searchRepository.setFavoriteStatusForItem(link, false)
            favoritesRepository.removeLinkFromFavorites(link)
        }
    }
}