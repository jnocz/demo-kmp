package database.convertes


import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class RoomTypeConverters {

    @TypeConverter
    fun toLocalDateTime(long: Long): LocalDateTime {
        return Instant.fromEpochMilliseconds(long).toLocalDateTime(TimeZone.currentSystemDefault())
    }

    @TypeConverter
    fun toLong(localDateTime: LocalDateTime): Long {
        return localDateTime.toInstant(TimeZone.currentSystemDefault())
            .toEpochMilliseconds()
    }
}



