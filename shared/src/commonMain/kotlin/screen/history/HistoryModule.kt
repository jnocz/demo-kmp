package screen.history

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val historyModule
    get() = module {
        viewModelOf(::HistoryViewModel)
    }
