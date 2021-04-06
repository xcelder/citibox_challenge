package com.example.framework.sources.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "page")
internal data class DbPage(
    @PrimaryKey val pageId: Long = 0,
    val page: Int
)
