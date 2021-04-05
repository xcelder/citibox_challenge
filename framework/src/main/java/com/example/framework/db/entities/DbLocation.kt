package com.example.framework.db.entities

import androidx.room.ColumnInfo

data class DbLocation(
    @ColumnInfo(name = "location_name") val name: String,
    @ColumnInfo(name = "location_url") val url: String
)