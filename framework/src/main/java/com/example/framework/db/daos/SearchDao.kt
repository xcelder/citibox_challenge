package com.example.framework.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.framework.db.entities.DbSearch

@Dao
interface SearchDao {

    @Query("SELECT COUNT(*) FROM search WHERE search LIKE :search")
    fun itemsWithSearch(search: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearch(search: DbSearch)
}