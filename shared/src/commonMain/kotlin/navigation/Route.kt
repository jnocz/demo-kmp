package navigation

import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.favorites
import bazarsearchmultiplatform.shared.generated.resources.history
import bazarsearchmultiplatform.shared.generated.resources.ic_heart_red_small
import bazarsearchmultiplatform.shared.generated.resources.ic_history_black_24dp
import bazarsearchmultiplatform.shared.generated.resources.ic_search_black_24dp
import bazarsearchmultiplatform.shared.generated.resources.search
import bazarsearchmultiplatform.shared.generated.resources.tab_item_favorite
import bazarsearchmultiplatform.shared.generated.resources.tab_item_history
import bazarsearchmultiplatform.shared.generated.resources.tab_item_search
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

@Serializable
sealed class Route {

    @Serializable
    data object Main : Route()

    @Serializable
    data class Search(val initPhrase: String? = "") : Route()

    @Serializable
    data object Favorite : Route()

    @Serializable
    data object History : Route()
}

sealed class BottomNavItem(
     var title: StringResource,
     var unselectedIcon: DrawableResource,
    var selectedIcon: DrawableResource,
    var screenRoute: Route,
    var appBarTitle: StringResource,
    var badgeAmount: Int? = null
) {
    data object Search : BottomNavItem(
        Res.string.tab_item_search,
        Res.drawable.ic_search_black_24dp,
        Res.drawable.ic_search_black_24dp,
        Route.Search(""),
        Res.string.search
    )

    data object Favorite : BottomNavItem(
        Res.string.tab_item_favorite,
        Res.drawable.ic_heart_red_small,
        Res.drawable.ic_heart_red_small,
        Route.Favorite,
        Res.string.favorites
    )

    data object History : BottomNavItem(
        Res.string.tab_item_history,
        Res.drawable.ic_history_black_24dp,
        Res.drawable.ic_history_black_24dp,
        Route.History,
        Res.string.history
    )
}