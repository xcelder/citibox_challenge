package com.example.framework.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListTypeConverter {

    @TypeConverter
    fun fromString(jsonValue: String): List<String> =
        Gson().fromJson<List<String>>(jsonValue, object : TypeToken<List<String>>() {}.javaClass)

    @TypeConverter
    fun toString(list: List<String>): String = Gson().toJson(list)
}