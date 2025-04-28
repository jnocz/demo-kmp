package screen.search

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val searchModule
    get() = module {
        viewModelOf(::SearchViewModel)
    }
