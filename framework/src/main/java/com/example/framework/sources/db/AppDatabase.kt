package com.example.framework.sources.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.framework.sources.db.daos.CharactersDao
import com.example.framework.sources.db.daos.EpisodesDao
import com.example.framework.sources.db.entities.DbCharacter
import com.example.framework.sources.db.entities.DbCharactersPageCrossRef
import com.example.framework.sources.db.entities.DbEpisode
import com.example.framework.sources.db.entities.DbPage
import com.example.framework.sources.db.typeconverters.DateTypeConverter
import com.example.framework.sources.db.typeconverters.IntListTypeConverter
import com.example.framework.sources.db.typeconverters.StringListTypeConverter

internal const val DB_NAME = "app-database"

@Database
    (entities = [DbCharacter::class, DbPage::class, DbCharactersPageCrossRef::class, DbEpisode::class],
    version = 1
)
@TypeConverters(StringListTypeConverter::class, DateTypeConverter::class, IntListTypeConverter::class)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao

    abstract fun episodesDao(): EpisodesDao
}