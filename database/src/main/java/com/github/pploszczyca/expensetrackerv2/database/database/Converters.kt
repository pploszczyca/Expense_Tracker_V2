package com.github.pploszczyca.expensetrackerv2.database.database

import androidx.room.TypeConverter
import java.util.Date

internal class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}