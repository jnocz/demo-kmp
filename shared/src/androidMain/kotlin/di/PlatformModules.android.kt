package di

import androidx.room.RoomDatabase
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.liftric.kvault.KVault
import database.AppDatabase
//import database.firebase.FirebaseDatabaseObserver
import database.getDatabaseBuilder
import datasource.local.dataStore
import datasource.secure.KVaultSecureDataStoreProvider
import datasource.secure.SecureDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModules: List<Module>
    get() = listOf(
        module {
            single { dataStore(context = get()) }
            single<SecureDataStore> {
                val masterKey = MasterKey.Builder(androidContext())
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build()

                val sharedPreferences = EncryptedSharedPreferences.create(
                    androidContext(),
                    "bazar_search",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )

                SecureDataStore(
                    // EncryptedSharedPreferencesDataSourceProvider(sharedPreferences)
                    KVaultSecureDataStoreProvider(
                        KVault(androidContext(), NameSessionSettings)
                    )
                )
            }
            single<RoomDatabase.Builder<AppDatabase>> {
                getDatabaseBuilder(get())
            }
        },
    )


