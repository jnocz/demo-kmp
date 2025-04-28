package screen.settings

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val settingsModule
    get() = module {
        viewModelOf(::SettingsViewModel)
    }
