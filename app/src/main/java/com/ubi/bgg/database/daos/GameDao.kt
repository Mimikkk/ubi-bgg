package com.ubi.bgg.database.daos

import androidx.room.*
import com.ubi.bgg.database.entities.Game
import com.ubi.bgg.database.entities.Rank

@Dao
interface GameDao {
  @Query("select * from Game")
  fun readAll(): List<Game>

  @Query("select * from Game where id = :id or BGGId = :id")
  fun read(id: Long): Game

  @Query("select * from Rank where gameId = :id")
  fun ranks(id: Long): List<Rank>

  @Query("select exists (select 1 from Game where id = :id or BGGId = :id)")
  fun contains(id: Long): Boolean

  @Delete
  fun remove(game: Game)

  @Update
  fun update(vararg games: Game)

  @Insert
  fun create(game: Game): Long

  @Insert
  fun create(vararg games: Game): List<Long>
}
