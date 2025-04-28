package datasource.favorite

import org.koin.dsl.module

internal val favoritesDataSourceModule
    get() = module {
        single<FavoritesDataSource> { FavoritesDataSourceImpl(get(), get()) }
    }