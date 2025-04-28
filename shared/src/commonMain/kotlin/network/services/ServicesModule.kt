package network.services

import de.jensklingenberg.ktorfit.Ktorfit
import network.services.search.SearchService
import network.services.search.createSearchService
import org.koin.dsl.module

val servicesModule = module {
    single { providesSearchService(get()) }
}

fun providesSearchService(ktorfit: Ktorfit): SearchService {
    return ktorfit.createSearchService()
}
