package com.example.framework.sources.db.entities

import androidx.room.*
import com.example.domain.entities.Character

@Entity(
    tableName = "character",
    indices = [Index(
        value = ["network_id", "name", "species", "type", "location_url"],
        unique = true
    )]
)
internal data class DbCharacter(
    @PrimaryKey(autoGenerate = true) val characterId: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "network_id") val networkId: Long,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @Embedded val location: DbLocation,
    @ColumnInfo(name = "species") val species: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "episode") val episode: List<Int>
)

internal fun DbCharacter.toDomainCharacter() = Character(
    id = networkId,
    name = name,
    imageUrl = imageUrl,
    location = location.toDomainLocation(),
    species = species,
    type = type,
    episode = episode
)

internal fun Character.toDbCharacter() = DbCharacter(
    networkId = id,
    name = name,
    imageUrl = imageUrl,
    location = location.toDbLocation(),
    species = species,
    type = type,
    episode = episode
)
