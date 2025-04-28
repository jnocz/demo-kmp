package datasource.search_result

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import co.touchlab.kermit.Logger
import database.AppDatabase
import database.search.model.DBSearchResult
import database.search.model.toSearchResult
import di.AppCoroutineDispatchers
import engine.domain.SearchResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchResultDataSourceImpl(
    private val db: AppDatabase,
    private var dispatchers: AppCoroutineDispatchers,
) : SearchResultDataSource {

    override fun deleteAllSearchResultsInDB() {
        CoroutineScope(dispatchers.database).launch {
            deleteAll()
        }
    }

    val PAGE_SIZE: Int = 30

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeSearchResultsDataSourceWithProps(
        location: String, priceFrom: Int?, priceTo: Int?
    ): Flow<PagingData<SearchResult>> {

        return Pager(
            config = PagingConfig(
                PAGE_SIZE
            ), pagingSourceFactory = { getDataSource(location, priceFrom, priceTo) }
        ).flow.mapLatest { pagingData -> pagingData.map { it.toSearchResult() } }
    }

    private fun getDataSource(location: String, priceFrom: Int?, priceTo: Int?) = when {

        location.isBlank() && bothPricesAreNull(
            priceFrom, priceTo
        ) -> db.searchDao().getSearchResultsDataSourceByRelevance()

        location.isNotBlank() && atLeastOnePriceIsNotNull(priceFrom, priceTo) -> {
            db.searchDao()
                .getSearchResultsDataSourceByRelevanceAndLocationAndPrices(
                    location, priceFrom ?: 0, priceTo ?: Int.MAX_VALUE
                )
        }

        location.isNotBlank() && bothPricesAreNull(
            priceFrom, priceTo
        ) -> db.searchDao().getSearchResultsDataSourceByRelevanceAndLocation(
            location
        )

        location.isBlank() && atLeastOnePriceIsNotNull(
            priceFrom, priceTo
        ) -> db.searchDao().getSearchResultsDataSourceByRelevanceAndPrices(
            priceFrom ?: 0, priceTo ?: Int.MAX_VALUE
        )

        else -> throw RuntimeException("Unsupported combination of values!")
    }


    private fun bothPricesAreNull(priceFrom: Int?, priceTo: Int?) =
        priceFrom == null && priceTo == null

    private fun atLeastOnePriceIsNotNull(priceFrom: Int?, priceTo: Int?) =
        priceFrom != null || priceTo != null


    override suspend fun deleteAllAndInsert(parsedResults: List<DBSearchResult>): Unit =
        withContext(dispatchers.database) {
            try {
                db.searchDao().deleteAll()
                db.searchDao().insertAll(parsedResults)
            } catch (e: Exception) {
                Logger.e(e) { "" }
            }
        }


    override suspend fun deleteAll(): Unit = withContext(dispatchers.database) {
        try {
            db.searchDao().deleteAll()
        } catch (e: Exception) {
            Logger.e(e) { e.message.toString() }
        }
    }

    override suspend fun insertAll(parsedResults: MutableList<DBSearchResult>): Unit =
        withContext(dispatchers.database) {
            try {
                db.searchDao().insertAll(parsedResults)
            } catch (e: Exception) {
                Logger.e(e) { e.message.toString() }
            }
        }

    override suspend fun setSearchItemWithLinkAsFavorite(link: String) {
        withContext(dispatchers.database) {
            try {
                val dbSearchResult = db.searchDao().getSearchResultByLink(link)
                if (dbSearchResult != null) {
                    db.searchDao().updateIsFavorite(dbSearchResult.id, true)
                }
            } catch (e: Exception) {
                Logger.e(e) { e.message.toString() }
            }
        }
    }

    override suspend fun setItemWithLinkAsNotFavorite(link: String) {
        withContext(dispatchers.database) {
            try {
                val dbSearchResult = db.searchDao().getSearchResultByLink(link)
                if (dbSearchResult != null) {
                    db.searchDao().updateIsFavorite(dbSearchResult.id, false)
                }
            } catch (e: Exception) {
                Logger.e(e) { e.message.toString() }
            }
        }
    }

}