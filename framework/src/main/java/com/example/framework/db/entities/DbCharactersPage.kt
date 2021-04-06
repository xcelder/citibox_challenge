package com.example.framework.db.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

internal data class DbCharactersPage(
    @Embedded val page: DbPage,
    @Relation(
        parentColumn = "page",
        entityColumn = "characterId",
        associateBy = Junction(DbCharactersPageCrossRef::class)
    )
    val characters: List<DbCharacter>
)