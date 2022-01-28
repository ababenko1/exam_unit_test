package com.example.testapplication.presentation.util

import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter


private fun dateTimeString(startTime: ZonedDateTime): String {
    return DateTimeFormatter.ofPattern("YYYY, MMM d, h:mm a").format(startTime)
}

private fun convertServerDateString(serverDateString: String?): ZonedDateTime {
    return ZonedDateTime.parse(serverDateString)
        .withZoneSameInstant(ZoneId.systemDefault())
}

fun getDateCreation(serverDateString: String?): String {
    val serverDate = convertServerDateString(serverDateString)
    return dateTimeString(serverDate)
}

