package database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Upsert

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(obj: List<T>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(obj: T): Int

    @Upsert
    suspend fun upsert(obj: T)

    @Upsert
    suspend fun upsertAll(obj: List<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAll(users: List<T>): Int

    @Delete
    suspend fun delete(obj: T): Int

}