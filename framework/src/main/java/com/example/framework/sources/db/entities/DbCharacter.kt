package com.example.framework.sources.db.entities

import androidx.room.*
import com.example.domain.entities.Character

@Entity(
    tableName = "character",
    indices = [Index(
        value = ["name", "species", "type", "location_name"],
        unique = true
    )]
)
internal data class DbCharacter(
    @PrimaryKey(autoGenerate = true) val characterId: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @Embedded val location: DbLocation,
    @ColumnInfo(name = "species") val species: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "episode") val episode: List<String>
)

internal fun DbCharacter.toDomainCharacter() = Character(
    name = name,
    imageUrl = imageUrl,
    location = location.toDomainLocation(),
    species = species,
    type = type,
    episode = episode
)

internal fun Character.toDbCharacter() = DbCharacter(
    name = name,
    imageUrl = imageUrl,
    location = dbLocationFromDomainLocation(location),
    species = species,
    type = type,
    episode = episode
)
