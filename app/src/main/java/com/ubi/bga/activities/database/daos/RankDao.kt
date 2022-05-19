package com.ubi.bga.activities.database.daos

import androidx.room.*
import com.ubi.bga.activities.database.entities.Rank

@Dao
interface RankDao {
  @Query("select * from Rank")
  fun readAll(): List<Rank>

  @Query("select * from Rank where id = :id")
  fun read(id: Long): Rank

  @Delete
  fun remove(Rank: Rank)

  @Update
  fun update(vararg Ranks: Rank)

  @Insert
  fun create(Rank: Rank): Long

  @Insert
  fun create(vararg Ranks: Rank): List<Long>
}
