import co.touchlab.kermit.Logger
import database.favorites.model.DBFavoriteItem
import datasource.favorite.FavoritesDataSource
import datasource.search_result.SearchResultDataSource
import di.AppCoroutineDispatchers
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
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
import repository.favorites.FavoritesRepository
import repository.favorites.FavoritesRepositoryImpl
import utils.TestCoroutineDispatchers
import kotlin.test.assertNotNull

class FavoritesRepositoryTest
    : KoinTest {

    private val testDispatchers = TestCoroutineDispatchers()

    private val favoritesDataSource: FavoritesDataSource = mockk(relaxed = true)
    private val searchDataSource: SearchResultDataSource = mockk(relaxed = true)
    private val favoritesRepository: FavoritesRepository by inject()

    private lateinit var testScope: TestScope

    private val overrideModule = module {
        single<FavoritesDataSource> { favoritesDataSource }
        single<SearchResultDataSource> { searchDataSource }
        single<AppCoroutineDispatchers> { testDispatchers }
        single<FavoritesRepository> { FavoritesRepositoryImpl(get(), get()) }
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
    fun addFavoriteItemTest() = testScope.runTest {
        val favoriteItem = DBFavoriteItem(123, "link", "")
        coEvery { favoritesDataSource.saveLinkToFavorites(favoriteItem.link) } returns favoriteItem
        coEvery { searchDataSource.setSearchItemWithLinkAsFavorite(favoriteItem.link) } just Runs
        val result = favoritesRepository.addLinkToFavorites(favoriteItem.link)
        assertNotNull(result)
        coVerify { searchDataSource.setSearchItemWithLinkAsFavorite(favoriteItem.link) }
    }


    @Test
    fun removeFavoriteItemTest() = testScope.runTest {
        val favoriteItem = DBFavoriteItem(123, "link", "")
        coEvery { favoritesDataSource.removeLinkFromFavorites(favoriteItem.link) } returns true
        coEvery { searchDataSource.setItemWithLinkAsNotFavorite(favoriteItem.link) } just Runs
        val isRemoved = favoritesRepository.removeLinkFromFavorites(favoriteItem.link)
        assertTrue(isRemoved)
        coVerify { searchDataSource.setItemWithLinkAsNotFavorite(favoriteItem.link) }
    }

    @After
    fun stopKoinAfterTest() {
        Logger.d { "stopKoin After Test" }
        stopKoin()
    }

}