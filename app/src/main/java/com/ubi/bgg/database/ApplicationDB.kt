package com.ubi.bgg.database

import androidx.room.*
import com.ubi.bgg.database.daos.GameDao
import com.ubi.bgg.database.daos.RankDao
import com.ubi.bgg.database.entities.Game
import com.ubi.bgg.database.entities.Rank

@Database(
  entities = [Game::class, Rank::class],
  version = 1,
  exportSchema = false
)
abstract class ApplicationDB : RoomDatabase() {
  abstract fun ranks(): RankDao
  abstract fun games(): GameDao
}
