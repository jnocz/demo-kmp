package screen.donate

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val donateModule
    get() = module {
        viewModelOf(::DonateViewModel)
    }
