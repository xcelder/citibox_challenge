package com.example.framework.sources.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entities.Episode
import java.util.*

@Entity(tableName = "episode")
internal data class DbEpisode(
    @PrimaryKey(autoGenerate = true) val episodeId: Long = 0,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "air_date") val airDate: Date,
    @ColumnInfo(name = "characters") val characters: List<String>
)

internal fun DbEpisode.toDomainEpisode() = Episode(id, airDate, characters)

internal fun dbEpisodeFromDomainEpisode(episode: Episode) = with(episode) {
    DbEpisode(
        id = id,
        airDate = airDate,
        characters = characters
    )
}