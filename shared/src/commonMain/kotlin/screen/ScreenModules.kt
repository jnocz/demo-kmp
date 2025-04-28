package screen

import screen.about.aboutModule
import screen.donate.donateModule
import screen.favorites.favoritesModule
import screen.history.historyModule
import screen.search.searchModule
import screen.settings.settingsModule

val screenModules
    get() = listOf(
        aboutModule,
        donateModule,
        favoritesModule,
        historyModule,
        searchModule,
        settingsModule
    )
