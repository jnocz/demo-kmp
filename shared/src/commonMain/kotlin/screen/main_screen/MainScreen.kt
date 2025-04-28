package screen.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.ic_heart_red_small
import navigation.BottomNavItem
import navigation.BottomNavigationBar
import navigation.Route
import org.jetbrains.compose.resources.painterResource
import screen.about.AboutScreen
import screen.donate.DonateScreen
import screen.favorites.FavoritesScreen
import screen.history.HistoryScreen
import screen.search.SearchScreen
import screen.settings.SettingsScreen
import utils.toColorInt

@Composable
fun MainScreen() {

    val tabNavHostController = rememberNavController()

    val selectedScreen = remember { mutableIntStateOf(0) }

    val currentBottomItem = remember {
        mutableStateOf<BottomNavItem>(BottomNavItem.Search)
    }

    val bottomItems = listOf(
        BottomNavItem.Search,
        BottomNavItem.Favorite,
        BottomNavItem.History
    )

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            Column {
                HorizontalDivider(thickness = 0.3.dp, color = Color.LightGray)
                BottomNavigationBar(
                    bottomItems,
                    selectedScreen,
                    navController = tabNavHostController,
                    onNavigateToDestination = { destinationNavItem, destinationTitle ->
                        currentBottomItem.value = destinationNavItem
                    }
                )
            }
        }) { innerPadding ->

        TabNavigation(
            tabNavHostController,
            { selectedScreen.value = it }
        )
    }

}

@Composable
fun TabNavigation(
    navController: NavHostController,
    onNavigateToDestination: (selectedScreenIndex: Int) -> Unit
) {

    NavHost(
        navController,
        startDestination = Route.Search(),
        modifier = Modifier.fillMaxSize(),
    ) {
        composable<Route.Search>
        { backStackEntry ->
            val search: Route.Search = backStackEntry.toRoute<Route.Search>()
            SearchScreen(initPhrase = search.initPhrase)
        }
        composable<Route.Favorite>{
            FavoritesScreen()
        }
        composable<Route.History> {
            HistoryScreen(
                onNavigateToSearchScreen = { searchedText ->
                    navController.navigate(
                        route = Route.Search(searchedText)
                    )
                    onNavigateToDestination(0)
                })
        }
    }
}
