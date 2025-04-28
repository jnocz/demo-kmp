package datasource.local

import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    var searchBazosCZ: Flow<Boolean>
    var searchBazosSK: Flow<Boolean>
    var searchBazosPL: Flow<Boolean>
    var searchHudebniBazar: Flow<Boolean>
    var searchSBazar: Flow<Boolean>
    var searchLetgo: Flow<Boolean>
    var searchAnnonce: Flow<Boolean>
    var searchHyperinzerce: Flow<Boolean>
    var searchBazarCz: Flow<Boolean>
    var searchMimiBazar: Flow<Boolean>
    var searchAntikOlomouc: Flow<Boolean>
    var searchAvizo: Flow<Boolean>
    var searchBazarSK: Flow<Boolean>
    var searchIFaunaCZ: Flow<Boolean>
    var searchOlxPl: Flow<Boolean>
    var searchSprzedajemyPl: Flow<Boolean>
    var searchEbayKleinanzeigenDe: Flow<Boolean>
    var searchSubitoIt: Flow<Boolean>
    var searchPoshmarkCom: Flow<Boolean>
    var searchPoshmarkCa: Flow<Boolean>
    var searchVivaStreetCoUk: Flow<Boolean>
    var loaderImageNumber: Flow<Int>
    var displayInitSearchPage: Flow<Boolean>
    var location: Flow<String>
    var priceFrom: Flow<String>
    var priceTo: Flow<String>

    suspend fun setSearchBazosCZ(value: Boolean)
    suspend fun setSearchBazosSK(value: Boolean)
    suspend fun setSearchBazosPL(value: Boolean)
    suspend fun setSearchHudebniBazar(value: Boolean)
    suspend fun setSearchSBazar(value: Boolean)
    suspend fun setSearchLetgo(value: Boolean)
    suspend fun setSearchAnnonce(value: Boolean)
    suspend fun setSearchHyperinzerce(value: Boolean)
    suspend fun setSearchBazarCz(value: Boolean)
    suspend fun setSearchMimiBazar(value: Boolean)
    suspend fun setSearchAntikOlomouc(value: Boolean)
    suspend fun setSearchAvizo(value: Boolean)
    suspend fun setSearchBazarSK(value: Boolean)
    suspend fun setSearchIFaunaCZ(value: Boolean)
    suspend fun setSearchOlxPl(value: Boolean)
    suspend fun setSearchSprzedajemyPl(value: Boolean)
    suspend fun setSearchEbayKleinanzeigenDe(value: Boolean)
    suspend fun setSearchSubitoIt(value: Boolean)
    suspend fun setSearchPoshmarkCom(value: Boolean)
    suspend fun setSearchPoshmarkCa(value: Boolean)
    suspend fun setSearchVivaStreetCoUk(value: Boolean)
    suspend fun setLoaderImageNumber(value: Int)
    suspend fun setDisplayInitSearchPage(value: Boolean)
    suspend fun setLocation(value: String)
    suspend fun setPriceFrom(value: String)
    suspend fun setPriceTo(value: String)
}
