package com.example.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.framework.db.daos.CharactersDao
import com.example.framework.db.daos.SearchDao
import com.example.framework.db.entities.DbCharacter
import com.example.framework.db.entities.DbSearch
import com.example.framework.db.typeconverters.StringListTypeConverter

@Database(entities = [DbCharacter::class, DbSearch::class], version = 1)
@TypeConverters(StringListTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao

    abstract fun searchDao(): SearchDao
}