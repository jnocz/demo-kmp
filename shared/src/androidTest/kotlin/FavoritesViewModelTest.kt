import co.touchlab.kermit.Logger
import di.AppCoroutineDispatchers
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import repository.favorites.FavoritesRepository
import repository.search.SearchRepository
import screen.favorites.FavoritesViewModel
import screen.favorites.list.FavoriteItem
import utils.TestCoroutineDispatchers

class FavoritesViewModelTest
    : KoinTest {

    private val testDispatchers = TestCoroutineDispatchers()

    private val favoriteRepo: FavoritesRepository = mockk(relaxed = true)
    private val searchRepo: SearchRepository = mockk(relaxed = true)

    private val viewModel: FavoritesViewModel by inject()

    private lateinit var testScope: TestScope

    private val overrideModule = module {
        single<FavoritesRepository> { favoriteRepo }
        single<SearchRepository> { searchRepo }
        single<AppCoroutineDispatchers> { testDispatchers }
        viewModelOf(::FavoritesViewModel)
    }

    @Before
    fun setup() {
        Logger.d { "Setup BeforeTest" }
        testScope = TestScope()
//        loadKoinModules(overrideModule)

        startKoin {
            modules(overrideModule)
        }
    }

    @Test
    fun removeFavoriteItemTest() = testScope.runTest {
        val favoriteItem = FavoriteItem(123, "link")
        coEvery { favoriteRepo.removeLinkFromFavorites(favoriteItem.link) } returns true
        coEvery { favoriteRepo.existsFavoriteItemWithLink(favoriteItem.link) } returns false
        viewModel.removeFavoriteItemWithLink(favoriteItem.link)
    }


    @After
    fun stopKoinAfterTest() {
        Logger.d { "stopKoinAfterTest" }
        stopKoin()
    }

}