package navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import screen.main_screen.MainScreen


@Composable
fun Navigation(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {

    NavHost(
        navController = navHostController,
        startDestination = Route.Main,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() },
        modifier = modifier.fillMaxSize(),
    ) {

        composable<Route.Main> {
            MainScreen()
        }
    }
}
