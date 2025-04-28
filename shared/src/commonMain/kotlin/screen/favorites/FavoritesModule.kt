package screen.favorites

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val favoritesModule
    get() = module {
        viewModelOf(::FavoritesViewModel)
    }
