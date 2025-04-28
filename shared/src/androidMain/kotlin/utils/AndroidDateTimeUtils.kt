package utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDateTime.formatFullDateCustom(): String {

    val dateFormatter = DateTimeFormatter.ofPattern(
        FULL_DATE_CUSTOM, Locale.getDefault()
    )
    return dateFormatter.format(this)
}