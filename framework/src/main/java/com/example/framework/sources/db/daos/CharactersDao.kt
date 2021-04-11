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

    @Query("SELECT * FROM character WHERE network_id IN (:characterIds)")
    abstract suspend fun getCharacters(characterIds: List<Long>): List<DbCharacter>

    @Query("SELECT COUNT(*) FROM character WHERE network_id IN (:characterIds)")
    abstract suspend fun getCharactersCountIn(characterIds: List<Long>): Int

    suspend fun insertCharactersPage(charactersPage: DbCharactersPage) {
        val pageId = insertPage(charactersPage.page)
        val charactersId = insertCharacters(charactersPage.characters)

        val charactersPageCrossRefList = charactersId.map {
            DbCharactersPageCrossRef(it, pageId)
        }

        insertCharactersPageCrossRef(charactersPageCrossRefList)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertCharacters(characters: List<DbCharacter>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertPage(page: DbPage): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertCharactersPageCrossRef(charactersPageCrossRef: List<DbCharactersPageCrossRef>)
}