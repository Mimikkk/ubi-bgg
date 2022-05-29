package com.ubi.bgg.activities.database.daos

import androidx.room.*
import com.ubi.bgg.activities.database.entities.Extension

@Dao
interface ExtensionDao {
  @Query("select * from Extension")
  fun readAll(): List<Extension>

  @Query("select * from Extension where id = :id")
  fun read(id: Long): Extension

  @Delete
  fun remove(extension: Extension)

  @Update
  fun update(vararg extensions: Extension)

  @Insert
  fun create(extension: Extension): Long

  @Insert
  fun create(vararg extensions: Extension): List<Long>
}
