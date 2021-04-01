package com.elkir.scanner.constants

import java.util.regex.Pattern

object PatternConstants {
    val patternYandex = Pattern.compile("^(https?://)?(www\\.)?yadi\\.sk/i/[a-zA-Z0-9]+$")
    val patternYandexDisk =
        Pattern.compile("^(https?://)?(www\\.)?disk\\.yandex\\.ru/i/[a-zA-Z0-9]+$")
    val patternLocal = Pattern.compile("^(file://)?/.+")
}