package com.example.framework.sources.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.framework.sources.db.daos.CharactersDao
import com.example.framework.sources.db.daos.SearchDao
import com.example.framework.sources.db.entities.DbCharacter
import com.example.framework.sources.db.entities.DbCharactersPageCrossRef
import com.example.framework.sources.db.entities.DbPage
import com.example.framework.sources.db.entities.DbSearch
import com.example.framework.sources.db.typeconverters.DateTypeConverter
import com.example.framework.sources.db.typeconverters.StringListTypeConverter

@Database
    (entities = [DbCharacter::class, DbSearch::class, DbPage::class, DbCharactersPageCrossRef::class],
    version = 1
)
@TypeConverters(StringListTypeConverter::class, DateTypeConverter::class)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao

    abstract fun searchDao(): SearchDao
}