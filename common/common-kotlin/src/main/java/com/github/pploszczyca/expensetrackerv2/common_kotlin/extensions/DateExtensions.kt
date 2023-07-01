package com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val DATE_STRING_PATTERN: String = "yyyy-MM-dd"

private val simpleDateFormat = SimpleDateFormat(DATE_STRING_PATTERN, Locale.ENGLISH)

fun Date.toFormattedString(): String = simpleDateFormat.format(this)

fun String.toDate(): Date = simpleDateFormat.parse(this)!!