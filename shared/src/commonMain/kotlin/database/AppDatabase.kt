package database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL
import database.convertes.RoomTypeConverters
import database.convertes.SearchedPageConverter
import database.search.SearchDaoImpl
import database.favorites.FavoritesDaoImpl
import database.history.HistoryDaoImpl
import database.favorites.model.DBFavoriteItem
import database.history.model.DBHistoryItem
import database.search.model.DBSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@ConstructedBy(AppDatabaseConstructor::class)
@Database(
    entities = [DBFavoriteItem::class, DBHistoryItem::class, DBSearchResult::class],
    version = 12
)
@TypeConverters(
    RoomTypeConverters::class,
    SearchedPageConverter::class
)
abstract class AppDatabase : RoomDatabase(), DB {

    abstract fun favoritesDao(): FavoritesDaoImpl
    abstract fun historyDao(): HistoryDaoImpl
    abstract fun searchDao(): SearchDaoImpl

    override fun clearAllTables() {
        super.clearAllTables()
    }

}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

// FIXME: Added a hack to resolve below issue:
// Class 'AppDatabase_Impl' is not abstract and does not implement abstract base class member 'clearAllTables'.
interface DB {
    fun clearAllTables() {}
}

const val dbFileName = "bazarsearchapp.db"


fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        //.addMigrations(MIGRATIONS)
        //.fallbackToDestructiveMigrationOnDowngrade(true)
        .fallbackToDestructiveMigration(true)
        //.setDriver(BundledSQLiteDriver())
        .addCallback(TestDatabaseCallback())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}


internal class TestDatabaseCallback : RoomDatabase.Callback() {
    override fun onOpen(connection: SQLiteConnection) {
        connection.apply {
            execSQL("PRAGMA synchronous = NORMAL")
        }
    }
}