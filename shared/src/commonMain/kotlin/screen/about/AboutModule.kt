package screen.about

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val aboutModule
    get() = module {
        viewModelOf(::AboutViewModel)
    }
