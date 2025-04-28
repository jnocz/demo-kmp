package datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class LocalDataSourceImpl(private val dataStore: DataStore<Preferences>) : LocalDataSource {

    companion object {

        const val KEY_SEARCH_BAZOS_CZ = "SEARCH_BAZOS"
        const val KEY_SEARCH_BAZOS_SK = "SEARCH_BAZOS_SK"
        const val KEY_SEARCH_BAZOS_PL = "SEARCH_BAZOS_PL"
        const val KEY_SEARCH_HUDEBNI_BAZAR = "SEARCH_HUDEBNI_BAZAR"
        const val KEY_SEARCH_SBAZAR = "SEARCH_SBAZAR"
        const val KEY_SEARCH_LETGO = "SEARCH_LETGO"
        const val KEY_SEARCH_ANNONCE = "SEARCH_ANNONCE"
        const val KEY_SEARCH_HYPERINZERCE = "SEARCH_HYPERINZERCE"
        const val KEY_SEARCH_BAZARCZ = "SEARCH_BAZARCZ"
        const val KEY_SEARCH_MIMIBAZAR = "SEARCH_MIMIBAZAR"
        const val KEY_SEARCH_ANTIK_OLOMOUC = "SEARCH_ANTIK_OLOMOUC"
        const val KEY_SEARCH_AVIZO = "SEARCH_AVIZO"
        const val KEY_SEARCH_BAZAR_SK = "SEARCH_BAZAR_SK"
        const val KEY_SEARCH_IFAUNA_CZ = "SEARCH_IFAUNA_CZ"
        const val KEY_SEARCH_OLX_PL = "SEARCH_OLX_PL"
        const val KEY_SEARCH_SPRZEDAJEMY_PL = "SEARCH_SPRZEDAJEMY_PL"
        const val KEY_SEARCH_EBAY_KLEINANZEIGEN_DE = "EBAY_KLEINANZEIGEN_DE"
        const val KEY_SEARCH_SUBITO_IT = "SEARCH_SUBITO_IT"
        const val KEY_SEARCH_POSHMARK_COM: String = "POSHMARK_COM"
        const val KEY_SEARCH_POSHMARK_CA: String = "POSHMARK_CA"
        const val KEY_SEARCH_VIVASTREET_CO_UK = "VIVASTREET_CO_UK"
        const val KEY_LOCATION = "LOCATION"
        const val KEY_PRICE_FROM = "PRICE_FROM"
        const val KEY_PRICE_TO = "PRICE_TO"
        const val KEY_LOADER_IMAGE_NUMBER = "LOADER_IMAGE_NUMBER"
        const val KEY_DISPLAY_INIT_SEARCH_PAGE = "DISPLAY_INIT_SEARCH_PAGE"

    }

    private val bazosCZKey = booleanPreferencesKey(KEY_SEARCH_BAZOS_CZ)

    override var searchBazosCZ: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[bazosCZKey] ?: true
    }

    override suspend fun setSearchBazosCZ(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_BAZOS_CZ, value)
    }

    private val bazosSKKey = booleanPreferencesKey(KEY_SEARCH_BAZOS_SK)
    override var searchBazosSK: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[bazosSKKey] ?: true
    }

    override suspend fun setSearchBazosSK(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_BAZOS_SK, value)
    }

    private val bazosPLKey = booleanPreferencesKey(KEY_SEARCH_BAZOS_PL)
    override var searchBazosPL: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[bazosPLKey] ?: false
    }

    override suspend fun setSearchBazosPL(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_BAZOS_PL, value)
    }

    private val hudebniBazarKey = booleanPreferencesKey(KEY_SEARCH_HUDEBNI_BAZAR)
    override var searchHudebniBazar: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[hudebniBazarKey] ?: true
    }

    override suspend fun setSearchHudebniBazar(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_HUDEBNI_BAZAR, value)
    }

    private val sBazarKey = booleanPreferencesKey(KEY_SEARCH_SBAZAR)
    override var searchSBazar: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[sBazarKey] ?: true
    }

    override suspend fun setSearchSBazar(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_SBAZAR, value)
    }

    private val letgoKey = booleanPreferencesKey(KEY_SEARCH_LETGO)
    override var searchLetgo: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[letgoKey] ?: true
    }

    override suspend fun setSearchLetgo(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_LETGO, value)
    }

    private val annonceKey = booleanPreferencesKey(KEY_SEARCH_ANNONCE)
    override var searchAnnonce: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[annonceKey] ?: true
    }

    override suspend fun setSearchAnnonce(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_ANNONCE, value)
    }

    private val hyperinzerceKey = booleanPreferencesKey(KEY_SEARCH_HYPERINZERCE)
    override var searchHyperinzerce: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[hyperinzerceKey] ?: true
    }

    override suspend fun setSearchHyperinzerce(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_HYPERINZERCE, value)
    }

    private val bazarCzKey = booleanPreferencesKey(KEY_SEARCH_BAZARCZ)
    override var searchBazarCz: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[bazarCzKey] ?: true
    }

    override suspend fun setSearchBazarCz(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_BAZARCZ, value)
    }

    private val mimiBazarKey = booleanPreferencesKey(KEY_SEARCH_MIMIBAZAR)
    override var searchMimiBazar: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[mimiBazarKey] ?: true
    }

    override suspend fun setSearchMimiBazar(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_MIMIBAZAR, value)
    }

    private val antikOlomoucKey = booleanPreferencesKey(KEY_SEARCH_ANTIK_OLOMOUC)
    override var searchAntikOlomouc: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[antikOlomoucKey] ?: true
    }

    override suspend fun setSearchAntikOlomouc(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_ANTIK_OLOMOUC, value)
    }

    // Avizo
    private val avizoKey = booleanPreferencesKey(KEY_SEARCH_AVIZO)
    override var searchAvizo: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[avizoKey] ?: true
    }

    override suspend fun setSearchAvizo(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_AVIZO, value)
    }

    // BazarSK
    private val bazarSKKey = booleanPreferencesKey(KEY_SEARCH_BAZAR_SK)
    override var searchBazarSK: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[bazarSKKey] ?: true
    }

    override suspend fun setSearchBazarSK(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_BAZAR_SK, value)
    }

    // IFaunaCZ
    private val iFaunaCZKey = booleanPreferencesKey(KEY_SEARCH_IFAUNA_CZ)
    override var searchIFaunaCZ: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[iFaunaCZKey] ?: true
    }

    override suspend fun setSearchIFaunaCZ(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_IFAUNA_CZ, value)
    }

    // OlxPl
    private val olxPlKey = booleanPreferencesKey(KEY_SEARCH_OLX_PL)
    override var searchOlxPl: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[olxPlKey] ?: false
    }

    override suspend fun setSearchOlxPl(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_OLX_PL, value)
    }

    // SprzedajemyPl
    private val sprzedajemyPlKey = booleanPreferencesKey(KEY_SEARCH_SPRZEDAJEMY_PL)
    override var searchSprzedajemyPl: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[sprzedajemyPlKey] ?: false
    }

    override suspend fun setSearchSprzedajemyPl(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_SPRZEDAJEMY_PL, value)
    }

    // EbayKleinanzeigenDe
    private val ebayKleinanzeigenDeKey = booleanPreferencesKey(KEY_SEARCH_EBAY_KLEINANZEIGEN_DE)
    override var searchEbayKleinanzeigenDe: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[ebayKleinanzeigenDeKey] ?: false
        }

    override suspend fun setSearchEbayKleinanzeigenDe(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_EBAY_KLEINANZEIGEN_DE, value)
    }

    // SubitoIt
    private val subitoItKey = booleanPreferencesKey(KEY_SEARCH_SUBITO_IT)
    override var searchSubitoIt: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[subitoItKey] ?: false
    }

    override suspend fun setSearchSubitoIt(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_SUBITO_IT, value)
    }

    // PoshmarkCom
    private val poshmarkComKey = booleanPreferencesKey(KEY_SEARCH_POSHMARK_COM)
    override var searchPoshmarkCom: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[poshmarkComKey] ?: false
    }

    override suspend fun setSearchPoshmarkCom(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_POSHMARK_COM, value)
    }

    // PoshmarkCa
    private val poshmarkCaKey = booleanPreferencesKey(KEY_SEARCH_POSHMARK_CA)
    override var searchPoshmarkCa: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[poshmarkCaKey] ?: false
    }

    override suspend fun setSearchPoshmarkCa(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_POSHMARK_CA, value)
    }

    // VivaStreetCoUk
    private val vivaStreetCoUkKey = booleanPreferencesKey(KEY_SEARCH_VIVASTREET_CO_UK)
    override var searchVivaStreetCoUk: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[vivaStreetCoUkKey] ?: false
    }

    override suspend fun setSearchVivaStreetCoUk(value: Boolean) {
        storeBooleanValue(KEY_SEARCH_VIVASTREET_CO_UK, value)
    }

    private val loaderImageNumberKey = intPreferencesKey(KEY_LOADER_IMAGE_NUMBER)
    override var loaderImageNumber: Flow<Int> = dataStore.data.map { preferences ->
        preferences[loaderImageNumberKey] ?: 0
    }

    override suspend fun setLoaderImageNumber(value: Int) {
        storeIntValue(KEY_LOADER_IMAGE_NUMBER, value)
    }

    private val displayInitSearchPageKey = booleanPreferencesKey(KEY_DISPLAY_INIT_SEARCH_PAGE)
    override var displayInitSearchPage: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[displayInitSearchPageKey] ?: true
    }

    override suspend fun setDisplayInitSearchPage(value: Boolean) {
        storeBooleanValue(KEY_DISPLAY_INIT_SEARCH_PAGE, value)
    }

    private val locationKey = stringPreferencesKey(KEY_LOCATION)
    override var location: Flow<String> = dataStore.data.map { preferences ->
        preferences[locationKey] ?: ""
    }

    override suspend fun setLocation(value: String) {
        storeStringValue(KEY_LOCATION, value)
    }

    private val priceFromKey = stringPreferencesKey(KEY_PRICE_FROM)
    override var priceFrom: Flow<String> = dataStore.data.map { preferences ->
        preferences[priceFromKey] ?: ""
    }

    override suspend fun setPriceFrom(value: String) {
        storeStringValue(KEY_PRICE_FROM, value)
    }

    private val priceToKey = stringPreferencesKey(KEY_PRICE_TO)
    override var priceTo: Flow<String> = dataStore.data.map { preferences ->
        preferences[priceToKey] ?: ""
    }

    override suspend fun setPriceTo(value: String) {
        storeStringValue(KEY_PRICE_TO, value)
    }

    private suspend fun storeStringValue(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    private suspend fun storeBooleanValue(key: String, value: Boolean) {
        val preferencesKey = booleanPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    private suspend fun storeIntValue(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

}
