import co.touchlab.kermit.Logger
import database.favorites.model.DBFavoriteItem
import datasource.favorite.FavoritesDataSource
import datasource.local.LocalDataSource
import datasource.parsed_search_result.ParsedSearchResultsRemoteDataSource
import datasource.search_result.SearchResultDataSource
import di.AppCoroutineDispatchers
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import repository.search.SearchRepository
import repository.search.SearchRepositoryImpl
import utils.TestCoroutineDispatchers

class SearchRepositoryTest
    : KoinTest {

    private val testDispatchers = TestCoroutineDispatchers()

    private val searchDataSource: SearchResultDataSource = mockk(relaxed = true)
    private val parsedSearchResultsRemoteDataSource: ParsedSearchResultsRemoteDataSource =
        mockk(relaxed = true)
    private val localDataSource: LocalDataSource = mockk(relaxed = true)
    private val searchResultDataSource: SearchResultDataSource = mockk(relaxed = true)
    private val favoritesDataSource: FavoritesDataSource = mockk(relaxed = true)
    private val searchRepository: SearchRepository by inject()

    private lateinit var testScope: TestScope

    private val overrideModule = module {
        single<SearchResultDataSource> { searchDataSource }
        single<AppCoroutineDispatchers> { testDispatchers }
        single<ParsedSearchResultsRemoteDataSource> { parsedSearchResultsRemoteDataSource }
        single<LocalDataSource> { localDataSource }
        single<SearchResultDataSource> { searchResultDataSource }
        single<FavoritesDataSource> { favoritesDataSource }
        single<SearchRepository> { SearchRepositoryImpl(get(), get(), get(), get(), get()) }
    }

    @Before
    fun setup() {
        Logger.d { "Setup Before Test" }
        testScope = TestScope()
//        loadKoinModules(overrideModule)

        startKoin {
            modules(overrideModule)
        }
    }


    @Test
    fun setFavoriteStatusForItemTest() = testScope.runTest {
        val favoriteItem = DBFavoriteItem(123, "link", "")
        coEvery { favoritesDataSource.saveLinkToFavorites(favoriteItem.link) } returns favoriteItem
        coEvery { searchResultDataSource.setSearchItemWithLinkAsFavorite(favoriteItem.link) } just Runs
        val result = searchRepository.setFavoriteStatusForItem(favoriteItem.link, true)
        assertTrue(result)
        //coVerify { searchDataSource.setSearchItemWithLinkAsFavorite(favoriteItem.link) }
    }

    @Test
    fun unfavoriteStatusForItemTest() = testScope.runTest {
        val favoriteItem = DBFavoriteItem(123, "link", "")
        coEvery { searchResultDataSource.setItemWithLinkAsNotFavorite(favoriteItem.link) } just Runs
        coEvery { favoritesDataSource.removeLinkFromFavorites(favoriteItem.link) } returns true
        val result = searchRepository.setFavoriteStatusForItem(favoriteItem.link, false)
        //runCurrent()
        assertTrue(result)
        //advanceUntilIdle()
        //coVerify { searchDataSource.setItemWithLinkAsNotFavorite(favoriteItem.link) }
    }


    @After
    fun stopKoinAfterTest() {
        Logger.d { "stopKoin After Test" }
        stopKoin()
    }

}