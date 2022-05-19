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
  fun remove(game: Game)

  @Update
  fun update(vararg games: Game)

  @Insert
  fun create(game: Game): Long

  @Insert
  fun create(vararg games: Game): List<Long>
}
