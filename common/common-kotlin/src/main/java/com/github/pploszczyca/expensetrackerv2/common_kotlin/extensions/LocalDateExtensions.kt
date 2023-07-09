package com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions

import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

fun LocalDate.toDate(): Date =
    Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
