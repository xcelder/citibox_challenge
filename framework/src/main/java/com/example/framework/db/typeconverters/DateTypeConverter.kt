package com.example.framework.db.typeconverters

import androidx.room.TypeConverter
import java.util.*

internal class DateTypeConverter {

    @TypeConverter
    fun fromTimestamp(value: Long): Date = Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time
}
