package datasource.history

import org.koin.dsl.module

internal val historyItemsDataSourceModule
    get() = module {
        single<HistoryItemsDataSource> {
            HistoryItemsDataSourceImpl(
                get(), get()
            )
        }
    }
