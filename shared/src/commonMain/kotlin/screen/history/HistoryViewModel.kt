/*
 * HistoryViewModel.kt
 * 22.10.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package screen.history

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
import repository.history.HistoryRepository
import screen.base.BaseViewModel
import screen.history.list.HistoryItem


class HistoryViewModel(
    private val dispatchers: AppCoroutineDispatchers,
    private val historyRepository: HistoryRepository
) : BaseViewModel() {

    private val _historyResults = MutableStateFlow<PagingData<HistoryItem>>(PagingData.empty())
    val historyResults: StateFlow<PagingData<HistoryItem>> = _historyResults.asStateFlow()

    private var initializeCalled = false

    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true
        refreshObservingHistoryRepository()
    }


    private fun refreshObservingHistoryRepository() {
        viewModelScope.launch(dispatchers.database) {
            historyRepository.observeDbPagingDataHistoryItems().distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _historyResults.value = it
                }
        }
    }
}