/*
 * SettingsViewModel.kt
 * 24.10.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package screen.settings

import androidx.annotation.MainThread
import androidx.lifecycle.viewModelScope
import datasource.local.LocalDataSource
import di.AppCoroutineDispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import screen.base.BaseViewModel
import utils.combine


data class SettingsUiState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val searchBazosCZ: Boolean = true,
    val searchBazosSK: Boolean = true,
    val searchBazosPL: Boolean = true,
    val searchSBazar: Boolean = true,
    val searchHudebniBazar: Boolean = true,
    val searchAnnonce: Boolean = true,
    val searchHyperinzerce: Boolean = true,
    val searchBazarCz: Boolean = true,
    val searchMimiBazar: Boolean = true,
    val searchAntikOlomouc: Boolean = true,
    val searchAvizo: Boolean = true,
    val searchBazarSK: Boolean = true,
    val searchOlxPl: Boolean = true,
    val searchSprzedajemyPl: Boolean = true,
    val searchEbayKleinanzeigenDe: Boolean = true,
    val searchSubitoIt: Boolean = true,
    val searchPoshmarkCom: Boolean = true,
    val searchPoshmarkCa: Boolean = true,
    val searchVivaStreetCoUk: Boolean = true,
    val location: String = "",
    val priceFrom: String = "",
    val priceTo: String = ""
)

class SettingsViewModel(
    private val localDataSource: LocalDataSource,
    val dispatchers: AppCoroutineDispatchers
) : BaseViewModel() {


    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    private var initializeCalled = false

    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true

        viewModelScope.launch(dispatchers.network) {
            combine(
                localDataSource.searchBazosCZ,
                localDataSource.searchBazosSK,
                localDataSource.searchBazosPL,
                localDataSource.searchSBazar,
                localDataSource.searchHudebniBazar,
                localDataSource.searchAnnonce,
                localDataSource.searchHyperinzerce,
                localDataSource.searchBazarCz,
                localDataSource.searchMimiBazar,
                localDataSource.searchAntikOlomouc,
                localDataSource.searchAvizo,
                localDataSource.searchBazarSK,
                localDataSource.searchOlxPl,
                localDataSource.searchSprzedajemyPl,
                localDataSource.searchEbayKleinanzeigenDe,
                localDataSource.searchSubitoIt,
                localDataSource.searchPoshmarkCom,
                localDataSource.searchPoshmarkCa,
                localDataSource.searchVivaStreetCoUk,
                localDataSource.location,
                localDataSource.priceFrom,
                localDataSource.priceTo

            ) { searchBazosCZ,
                searchBazosSK,
                searchBazosPL,
                searchSBazar,
                searchHudebniBazar,
                searchAnnonce,
                searchHyperinzerce,
                searchBazarCz,
                searchMimiBazar,
                searchAntikOlomouc,
                searchAvizo,
                searchBazarSK,
                searchOlxPl,
                searchSprzedajemyPl,
                searchEbayKleinanzeigenDe,
                searchSubitoIt,
                searchPoshmarkCom,
                searchPoshmarkCa,
                searchVivaStreetCoUk,
                location,
                priceFrom,
                priceTo
                ->

                SettingsUiState(
                    isLoading = false,
                    errorMessage = null,
                    searchBazosCZ,
                    searchBazosSK,
                    searchBazosPL,
                    searchSBazar,
                    searchHudebniBazar,
                    searchAnnonce,
                    searchHyperinzerce,
                    searchBazarCz,
                    searchMimiBazar,
                    searchAntikOlomouc,
                    searchAvizo,
                    searchBazarSK,
                    searchOlxPl,
                    searchSprzedajemyPl,
                    searchEbayKleinanzeigenDe,
                    searchSubitoIt,
                    searchPoshmarkCom,
                    searchPoshmarkCa,
                    searchVivaStreetCoUk,
                    location,
                    priceFrom,
                    priceTo
                )
            }
                .catch { throwable ->
                    _uiState.update { it.copy(errorMessage = throwable.message) }
                }.collect {
                    _uiState.value = it
                }
        }
    }


    fun updateSearchBazosCZ(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchBazosCZ(value)
        }
    }

    fun updateSearchBazosSK(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchBazosSK(value)
        }
    }


    fun updateSearchBazosPL(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchBazosPL(value)
        }
    }

    fun updateSearchSBazar(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchSBazar(value)
        }
    }


    fun updateSearchHudebniBazar(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchHudebniBazar(value)
        }
    }


    fun updateSearchAnnonce(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchAnnonce(value)
        }
    }

    fun updateSearchHyperinzerce(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchHyperinzerce(value)
        }
    }


    fun updateSearchBazarCZ(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchBazarCz(value)
        }
    }

    fun updateSearchMimiBazar(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchMimiBazar(value)
        }
    }

    fun updateSearchAntikOlomouc(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchAntikOlomouc(value)
        }
    }


    fun updateSearchAvizo(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchAvizo(value)
        }
    }


    fun updateSearchBazarSK(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchBazarSK(value)
        }
    }


    fun updateSearchOlxPl(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchOlxPl(value)
        }
    }


    fun updateSearchSprzedajemyPl(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchSprzedajemyPl(value)
        }
    }

    fun updateSearchEbayKleinanzeigenDe(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchEbayKleinanzeigenDe(value)
        }
    }

    fun updateSearchSubitoIt(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchSubitoIt(value)
        }
    }


    fun updateSearchPoshmarkCom(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchPoshmarkCom(value)
        }
    }

    fun updateSearchPoshmarkCa(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchPoshmarkCa(value)
        }
    }

    fun updateSearchVivaStreetCoUk(value: Boolean) {
        viewModelScope.launch {
            localDataSource.setSearchVivaStreetCoUk(value)
        }
    }

    fun updateLocation(value: String) {
        viewModelScope.launch {
            localDataSource.setLocation(value)
        }
    }

    fun updatePriceFrom(value: String) {
        viewModelScope.launch {
            localDataSource.setPriceFrom(value)
        }
    }

    fun updatePriceTo(value: String) {
        viewModelScope.launch {
            localDataSource.setPriceTo(value)
        }
    }
}