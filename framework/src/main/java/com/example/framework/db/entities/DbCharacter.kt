package com.example.framework.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entities.Location

@Entity(tableName = "character")
data class DbCharacter(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @Embedded val location: DbLocation,
    @ColumnInfo(name = "species") val species: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "episode") val episode: List<String>
)