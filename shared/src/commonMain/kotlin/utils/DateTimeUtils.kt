package utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.math.floor


const val FULL_DATE = "EEEE, dd MMMM yyyy"
const val FULL_DATE_WITH_MINUTES = "EEEE, d MMMM, yyyy h:mm a"
const val FULL_DATE_CUSTOM = "yyyyMMdd.HHmm"
const val HOUR_MINUTE = "HH:mm"
const val MONTH_DAY = "MMMM dd"
const val HOUR_MINUTE_SECOND = "HH:mm:ss"
const val MINUTES = "mm"
const val BASIC_ISO_DATE = "yyyy-MM-dd"
const val YEAR_MONTH_DATE = "yyyyMM"
const val YEAR_MONTH_SHORT = "yyyyM"
const val FULL_ISO_DATE = "yyyy-MM-dd'T'HH:mm:ss'Z'"
const val FULL_DATE_WITHOUT_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"

const val FULL_DATE_WITH_TIMEZONE_WITHOUT_COLON =
    "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ" //SSSSSSZ at the end +0200
const val FULL_DATE_WITH_TIMEZONE_WITH_COLON =
    "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX" // SSSSSSXXX means at the end +02:00
const val BASIC_DATE = "dd MMMM yyyy"
const val SHORT_DATE = "MMM dd, yyyy"
const val PRETTY_DAY_MONTH_TIME = "dd.M. HH:mm"
const val PRETTY_DATE_TIME_WITH_DASH = "dd.MM.yyyy - HH:mm"
const val PRETTY_DATE = "dd.MM.yyyy"
const val PRETTY_DATE_DASH = "dd-MM-yyyy"
const val PRETTY_DATE_TIME = "dd.MM.yyyy HH:mm"
const val PRETTY_DATE_TIME_WITH_SECONDS_WITHOUT_DASH = "dd.MM.yyyy HH:mm:ss"
const val PRETTY_DATE_TIME_WITH_SECONDS = "dd.MM.yyyy - HH:mm:ss"
const val PRETTY_DATE_TIME_WITH_SECONDS_WITH_PLUS = "dd.MM.yyyy+HH:mm:ss"


//TODO maybe use https://github.com/RaedGhazal/kotlinx-datetime-ext?tab=readme-ov-file ???


fun String.toLocalDateTime(): LocalDateTime {
    //2024-10-02T09:49:28.9056604
    return LocalDateTime.parse(
        input = this,
        //format = LocalDateTime.Format { byUnicodePattern(FULL_DATE_WITHOUT_TIMEZONE) }
        format = LocalDateTime.Formats.ISO
    )
}

/**
 * format Timestamp to Instant
 *
 * export const ticksToTime = (ticks) => {
 *     return Math.floor((ticks - 621355968000000000) / 10000000);
 * }
 *
 * from old system GINA
 *
 */
fun Long.formatTimestampToInstant(): Instant {
    return Instant.fromEpochMilliseconds(
        floor(((this - 621355968000000000) / 10000000).toDouble()).toLong()
    )
}

fun Long.formatPrettyDateTime(): String {
    return this.formatTimestampToInstant().toLocalDateTime(TimeZone.currentSystemDefault())
        .formatPrettyDateTime()
}

fun Long.toLocalDateTime(): LocalDateTime {
    return this.formatTimestampToInstant()
        .toLocalDateTime(TimeZone.currentSystemDefault())
}


fun LocalDateTime.formatPrettyDateTime(): String {
    val format = LocalDateTime.Format {
        dayOfMonth()
        char('.')
        monthNumber()
        char('.')
        year()
        char(' ')
        hour()
        char(':')
        minute()
        char(':')
        second()
    }

    return format.format(this)
}

fun LocalDate.formatPrettyDate(): String {
    val format = LocalDate.Format {
        dayOfMonth()
        char('.')
        monthNumber()
        char('.')
    }

    return format.format(this)
}

fun LocalTime.formatPrettyTime(): String {
    val format = LocalTime.Format {
        hour()
        char(':')
        minute()
    }

    return format.format(this)
}

fun LocalDateTime.formatCustomDateTime(): String {
    val format = LocalDateTime.Format {
        year()
        char('-')
        monthNumber()
        char('-')
        dayOfMonth()
        char(' ')
        hour()
        char(':')
        minute()
        char(':')
        second()
    }

    return format.format(this)
}

/**
 * `2021-08-30 18:43:12`
 */
fun LocalDateTime.formatDateTime(): String {
    val format = LocalDateTime.Format {
        date(LocalDate.Formats.ISO); char(' '); time(LocalTime.Formats.ISO)
    }
    return format.format(this)
}

/**
 * `2021-08-30 18:43:12`
 */
fun Instant.formatDateTime(): String {
    val format = LocalDateTime.Format {
        date(LocalDate.Formats.ISO); char(' '); time(LocalTime.Formats.ISO)
    }
    return format.format(this.toLocalDateTime(TimeZone.currentSystemDefault()))
}

fun Instant.formatDateTimeLong(): String {
    val format = LocalDateTime.Format {
        date(LocalDate.Formats.ISO); char(' '); time(LocalTime.Formats.ISO)
    }
    return format.format(this.toLocalDateTime(TimeZone.currentSystemDefault()))
}


/**
 * "20241023.0830"
 */
fun Instant.formatFullDateTime(): String {
    val format = LocalDateTime.Format {
        year()
        monthNumber()
        dayOfMonth()
        char('.')
        hour()
        minute()
    }

    return format.format(this.toLocalDateTime(TimeZone.currentSystemDefault()))
}
