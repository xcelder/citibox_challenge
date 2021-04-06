package com.example.framework.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entities.Character
import com.example.domain.entities.Location

@Entity(tableName = "character")
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

internal fun dbCharacterFromDomainCharacter(character: Character) = with(character) {
    DbCharacter(
        name = name,
        imageUrl = imageUrl,
        location = dbLocationFromDomainLocation(location),
        species = species,
        type = type,
        episode = episode
    )
}