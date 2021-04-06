package com.example.framework.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.framework.db.entities.DbEpisode

@Dao
internal interface EpisodesDao {

    @Query("SELECT * FROM episode WHERE id IN (:episodesNumber)")
    suspend fun getEpisodesByNumber(episodesNumber: List<Int>): List<DbEpisode>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<DbEpisode>)
}