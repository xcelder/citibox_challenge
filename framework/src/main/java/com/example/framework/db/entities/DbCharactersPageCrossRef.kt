package com.example.framework.db.entities

import androidx.room.Entity

@Entity(primaryKeys = ["characterId", "page"])
internal data class DbCharactersPageCrossRef(
    val characterId: Long,
    val page: Long
)