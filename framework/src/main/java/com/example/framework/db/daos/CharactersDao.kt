package com.example.framework.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.framework.db.entities.DbCharacter

@Dao
interface CharactersDao {

    @Query("SELECT COUNT(*) FROM character WHERE page = :page")
    fun itemsInPage(page: Int): Int

    @Query("SELECT * FROM character WHERE page = :page")
    fun getCharactersPage(page: Int): List<DbCharacter>

    @Query("SELECT * FROM character WHERE name LIKE :name")
    fun findCharactersByName(name: String): List<DbCharacter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(characters: List<DbCharacter>)
}