package com.example.framework.sources.db.daos

import androidx.room.*
import com.example.framework.sources.db.entities.DbCharacter
import com.example.framework.sources.db.entities.DbCharactersPage
import com.example.framework.sources.db.entities.DbCharactersPageCrossRef
import com.example.framework.sources.db.entities.DbPage

@Dao
internal abstract class CharactersDao {

    @Query("SELECT * FROM page WHERE page = :page")
    abstract suspend fun getPage(page: Int): List<DbPage>

    @Transaction
    @Query("SELECT * FROM page WHERE page = :page")
    abstract suspend fun getCharactersPage(page: Int): DbCharactersPage

    @Query("SELECT * FROM character WHERE name LIKE :name")
    abstract suspend fun findCharactersByName(name: String): List<DbCharacter>

    suspend fun insertCharactersPage(charactersPage: DbCharactersPage) {
        val pageId = insertPage(charactersPage.page)
        val charactersId = insertCharacters(charactersPage.characters)

        val charactersPageCrossRefList = charactersId.map {
            DbCharactersPageCrossRef(it, pageId)
        }

        insertCharactersPageCrossRef(charactersPageCrossRefList)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCharacters(characters: List<DbCharacter>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPage(page: DbPage): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCharactersPageCrossRef(charactersPageCrossRef: List<DbCharactersPageCrossRef>)
}