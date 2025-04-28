package di

import androidx.room.RoomDatabase
import com.liftric.kvault.KVault
import database.AppDatabase
import database.getDatabaseBuilder
import datasource.local.dataStore
import datasource.secure.KVaultSecureDataStoreProvider
import datasource.secure.SecureDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModules: List<Module>
    get() = listOf(
        module {
            single { dataStore() }
            single<SecureDataStore> {
                SecureDataStore(
                    KVaultSecureDataStoreProvider(
                        KVault(NameSessionSettings)
                    )
                )
            }
            single<RoomDatabase.Builder<AppDatabase>> {
                getDatabaseBuilder()
            }
        },
    )
