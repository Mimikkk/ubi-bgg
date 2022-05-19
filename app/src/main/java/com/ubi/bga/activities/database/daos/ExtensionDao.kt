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
  fun remove(Extension: Extension)

  @Update
  fun update(vararg Extensions: Extension)

  @Insert
  fun create(Extension: Extension): Long

  @Insert
  fun create(vararg Extensions: Extension): List<Long>
}
