package com.example.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.framework.db.daos.CharactersDao
import com.example.framework.db.daos.SearchDao
import com.example.framework.db.entities.DbCharacter
import com.example.framework.db.entities.DbCharactersPageCrossRef
import com.example.framework.db.entities.DbPage
import com.example.framework.db.entities.DbSearch
import com.example.framework.db.typeconverters.DateTypeConverter
import com.example.framework.db.typeconverters.StringListTypeConverter

@Database
    (entities = [DbCharacter::class, DbSearch::class, DbPage::class, DbCharactersPageCrossRef::class],
    version = 1
)
@TypeConverters(StringListTypeConverter::class, DateTypeConverter::class)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao

    abstract fun searchDao(): SearchDao
}