package repository

import org.koin.dsl.module
import repository.favorites.FavoritesRepository
import repository.favorites.FavoritesRepositoryImpl
import repository.history.HistoryRepository
import repository.history.HistoryRepositoryImpl
import repository.search.SearchRepository
import repository.search.SearchRepositoryImpl


internal val repositoriesModule
    get() = module {
        single<FavoritesRepository> { FavoritesRepositoryImpl(get(), get()) }
        single<HistoryRepository> { HistoryRepositoryImpl(get()) }
        single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
    }