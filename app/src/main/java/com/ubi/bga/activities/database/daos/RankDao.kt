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
  fun remove(rank: Rank)

  @Update
  fun update(vararg ranks: Rank)

  @Insert
  fun create(rank: Rank): Long

  @Insert
  fun create(vararg ranks: Rank): List<Long>
}
