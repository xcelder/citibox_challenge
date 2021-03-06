package com.example.framework.sources.db.typeconverters

import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal class StringListTypeConverter {

    @TypeConverter
    fun fromString(jsonValue: String?): List<String> =
        runCatching {
            jsonValue?.let {
                Gson().fromJson<List<String>>(it, object : TypeToken<List<String>>() {}.type)
            }
        }.getOrNull() ?: emptyList()

    @TypeConverter
    fun toString(list: List<String>): String = Gson().toJson(list)
}