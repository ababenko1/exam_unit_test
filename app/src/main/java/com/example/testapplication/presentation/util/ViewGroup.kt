package com.example.testapplication.presentation.util

import android.content.Context

fun Context.convertToDp(paddingRes: Int): Int {
    return if (paddingRes == 0) 0
    else resources.getDimension(paddingRes).toInt()
}