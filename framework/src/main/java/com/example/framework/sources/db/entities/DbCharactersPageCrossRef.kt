package com.example.framework.sources.db.entities

import androidx.room.Entity

@Entity(primaryKeys = ["characterId", "page"])
internal data class DbCharactersPageCrossRef(
    val characterId: Long,
    val page: Long
)