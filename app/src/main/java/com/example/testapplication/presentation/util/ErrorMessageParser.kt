package com.example.testapplication.presentation.util

import java.net.UnknownHostException

object ErrorMessageParser {

    fun parse(exception: Throwable?): String = when (exception) {
        is UnknownHostException -> SystemErrors.SERVER_CONNECTION
        else -> "Unknown error"
    }
}

object SystemErrors {
    const val SERVER_CONNECTION = "Server is temporary unavailable"
}