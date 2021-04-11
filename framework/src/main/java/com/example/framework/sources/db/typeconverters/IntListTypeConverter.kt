package com.example.framework.sources.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal class IntListTypeConverter {

    @TypeConverter
    fun fromString(jsonValue: String?): List<Int> =
        runCatching {
            jsonValue?.let {
                Gson().fromJson<List<Int>>(it, object : TypeToken<List<Int>>() {}.type)
            }
        }.getOrNull() ?: emptyList()

    @TypeConverter
    fun toString(list: List<Int>): String = Gson().toJson(list)
}