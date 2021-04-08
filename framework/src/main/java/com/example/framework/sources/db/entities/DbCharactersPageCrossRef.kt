package com.example.framework.sources.db.entities

import androidx.room.Entity

@Entity(primaryKeys = ["characterId", "pageId"])
internal data class DbCharactersPageCrossRef(
    val characterId: Long,
    val pageId: Long
)