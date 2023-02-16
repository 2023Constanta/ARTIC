package com.nightstalker.artic.core.presentation.ext

import android.os.Build
import android.text.Html
import com.nightstalker.artic.features.ApiConstants
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

/**
 * Функция для строк. Убирает тэги [Html]
 * @author Tamerlan Mamukhov on 2022-11-21
 */
fun String.filterHtmlEncodedText(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(replace("\n", "<br>"), Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(replace("\n", "<br>")).toString()
    }.apply {
        replace("\\</br.*?>", "\\\n")
    }
}

fun String.toCalendarInMillis(): Long {
    if (this.isEmpty()) return Date().time
    val localDateTime = LocalDateTime.parse(this, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    val zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault())
    return Date.from(zdt.toInstant()).time
}

// Перевод даты ISO 8601 в заданной формат
fun String.reformatIso8601(): String =
    SimpleDateFormat(ApiConstants.USER_FORMAT_DATE, Locale.getDefault())
        .format(
            this.toCalendarInMillis()
        )
