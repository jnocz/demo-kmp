package navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.theme.color.AppColorPalette

@Composable
fun BottomNavigationBar(
    destinations: List<BottomNavItem>,
    selectedTabIndex: MutableIntState,
    navController: NavController,
    onNavigateToDestination: (bottomNavItem: BottomNavItem, destinationTitle: String) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        tonalElevation = 15.dp,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        destinations.forEachIndexed { index, item ->

            val isSelected = index == selectedTabIndex.intValue
            val title = stringResource(resource = item.appBarTitle)

            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = AppColorPalette.gray,
                    unselectedTextColor = AppColorPalette.gray,
                    indicatorColor = Color.Transparent
                ),
                icon = {
                    TabBarIconView(
                        isSelected = isSelected,
                        selectedIcon = item.selectedIcon,
                        unselectedIcon = item.unselectedIcon,
                        title = item.title,
                        badgeAmount = item.badgeAmount
                    )

                },
                label = {
                    Text(
                        text = stringResource(resource = item.title).toUpperCase(Locale.current),
                        fontSize = if (isSelected) 11.sp else 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                alwaysShowLabel = true,
                selected = isSelected,//currentDestination?.hierarchy?.any { it.hasRoute(item.screenRoute::class) } == true,
                onClick = {
                    if (!isSelected) {
                        selectedTabIndex.intValue = index
                        onNavigateToDestination(item, title)

                        navController.navigate(route = item.screenRoute) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                })
        }
    }
}


@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: DrawableResource,
    unselectedIcon: DrawableResource,
    title: StringResource,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            painterResource(
                if (isSelected) {
                    selectedIcon
                } else {
                    unselectedIcon
                }
            ),
            contentDescription = "",
            modifier = Modifier.size(if (isSelected) 23.dp else 21.dp),
        )
    }
}

@Composable
fun TabBarBadgeView(count: Int? = null) {
    if (count != null) {
        Badge {
            Text(count.toString())
        }
    }
}