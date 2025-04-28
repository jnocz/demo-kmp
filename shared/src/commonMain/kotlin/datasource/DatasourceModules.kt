package datasource

import datasource.favorite.favoritesDataSourceModule
import datasource.history.historyItemsDataSourceModule
import datasource.local.localDataSourceModule
import datasource.search_result.searchResultDataSourceModule

val datasourceModules
    get() = listOf(
        localDataSourceModule,
        favoritesDataSourceModule,
        historyItemsDataSourceModule,
        searchResultDataSourceModule
    )