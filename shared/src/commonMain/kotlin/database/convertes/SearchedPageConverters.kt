package database.convertes

import androidx.room.TypeConverter
import engine.domain.SearchedPage

class SearchedPageConverter {

    @TypeConverter
    fun toSearchedPage(dbName: String): SearchedPage {
        return when (dbName) {
            SearchedPage.BAZOS_CZ.dbName -> SearchedPage.BAZOS_CZ
            else -> SearchedPage.BAZOS_CZ
        }
    }

    @TypeConverter
    fun toString(page: SearchedPage): String {
        return when (page) {
            SearchedPage.BAZOS_CZ -> page.dbName
            else -> throw RuntimeException("Unsupported value: ${page.pageName}")
        }
    }
}