package com.example.expensetrackerv2.extensions

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_STRING_PATTERN: String = "yyyy-MM-dd"

private val simpleDateFormat = SimpleDateFormat(DATE_STRING_PATTERN, Locale.ENGLISH)

fun Date.toFormattedString(): String = simpleDateFormat.format(this)

fun String.toDate(): Date = simpleDateFormat.parse(this)!!