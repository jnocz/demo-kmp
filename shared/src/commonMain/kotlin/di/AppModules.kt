package di

import database.AppDatabase
import database.getRoomDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module
import utils.event_bus.AppEventBus

val appModules = module {
    single { providesCorutineDispatcher() }
    single { provideDispatchers() }
    single { AppEventBus() }
    single<AppDatabase> { getRoomDatabase(get()) }
}


fun providesCorutineDispatcher(): CoroutineDispatcher {
    val backgroundPool: CoroutineDispatcher by lazy {
        Dispatchers.Default
    }
    return backgroundPool
}

fun provideDispatchers() = AppCoroutineDispatchers()

open class AppCoroutineDispatchers {
    open val database: CoroutineDispatcher by lazy { Dispatchers.Default }
    open val disk: CoroutineDispatcher by lazy { Dispatchers.Default }
    open val network: CoroutineDispatcher by lazy { Dispatchers.IO }
    open val main: CoroutineDispatcher by lazy { Dispatchers.Main }
}

open class TestCoroutineDispatchers : AppCoroutineDispatchers() {
    override val database: CoroutineDispatcher = Dispatchers.Unconfined
    override val disk: CoroutineDispatcher = Dispatchers.Unconfined
    override val network: CoroutineDispatcher = Dispatchers.Unconfined
    override val main: CoroutineDispatcher = Dispatchers.Unconfined
}


const val NameSessionSettings = "session-settings"