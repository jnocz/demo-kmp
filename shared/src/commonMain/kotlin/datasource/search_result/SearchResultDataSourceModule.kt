package datasource.search_result

import org.koin.dsl.module

internal val searchResultDataSourceModule
    get() = module {
        single<SearchResultDataSource> {
            SearchResultDataSourceImpl(
                get(), get()
            )
        }
    }
