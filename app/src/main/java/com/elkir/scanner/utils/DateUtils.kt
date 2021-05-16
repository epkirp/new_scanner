package com.elkir.scanner.utils

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDateAsString(): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val currentDate = Calendar.getInstance().time

    return sdf.format(currentDate)
}