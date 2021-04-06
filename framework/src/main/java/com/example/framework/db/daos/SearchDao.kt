package com.example.framework.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.framework.db.entities.DbSearch

@Dao
internal interface SearchDao {

    @Query("SELECT * FROM search WHERE :search LIKE search")
    suspend fun getSearch(search: String): List<DbSearch>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(search: DbSearch)
}
