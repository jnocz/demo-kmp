package datasource.secure

class SecureDataStore(private val secureDataStoreProvider: SecureDataStoreProvider) {

    fun clearAll() = secureDataStoreProvider.clear()
}