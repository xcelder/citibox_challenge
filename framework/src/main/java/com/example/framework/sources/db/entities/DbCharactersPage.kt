package com.example.framework.sources.db.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

internal data class DbCharactersPage(
    @Embedded val page: DbPage,
    @Relation(
        parentColumn = "pageId",
        entityColumn = "characterId",
        associateBy = Junction(DbCharactersPageCrossRef::class)
    )
    val characters: List<DbCharacter>
)