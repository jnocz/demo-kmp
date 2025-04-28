package datasource.local

import org.koin.dsl.module

internal val localDataSourceModule
    get() = module {
        single<LocalDataSource> { LocalDataSourceImpl(dataStore = get()) }
    }
