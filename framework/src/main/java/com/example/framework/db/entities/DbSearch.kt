package com.example.framework.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search")
data class DbSearch(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "search") val search: String
)