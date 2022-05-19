package com.ubi.bga.activities.database.daos

import androidx.room.*
import com.ubi.bga.activities.database.entities.Extension

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
