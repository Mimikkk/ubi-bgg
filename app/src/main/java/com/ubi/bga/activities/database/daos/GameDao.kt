package com.ubi.bga.activities.database.daos

import androidx.room.*
import com.ubi.bga.activities.database.entities.Game

@Dao
interface GameDao {
  @Query("select * from Game")
  fun readAll(): List<Game>

  @Query("select * from Game where id = :id")
  fun read(id: Long): Game

  @Delete
  fun remove(Game: Game)

  @Update
  fun update(vararg Games: Game)

  @Insert
  fun create(Game: Game): Long

  @Insert
  fun create(vararg Games: Game): List<Long>
}
