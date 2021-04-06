package com.example.framework.sources.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search")
internal data class DbSearch(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "search") val search: String
)