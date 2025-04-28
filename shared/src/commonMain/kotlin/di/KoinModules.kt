package di

import datasource.datasourceModules
import org.koin.core.KoinApplication
import screen.screenModules
import network.networkModule
import network.services.servicesModule
import repository.repositoriesModule

fun KoinApplication.initModules() {
    modules(platformModules)
    modules(appModules)
    modules(repositoriesModule)
    modules(networkModule)
    modules(servicesModule)
    modules(datasourceModules)
    modules(screenModules)
}
