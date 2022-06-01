package com.ubi.bgg.database

import androidx.room.*
import com.ubi.bgg.activities.database.daos.*
import com.ubi.bgg.activities.database.entities.*

@Database(
  entities = [Game::class, Extension::class, Rank::class],
  version = 1,
  exportSchema = false
)
abstract class ApplicationDB : RoomDatabase() {
  abstract fun ranks(): RankDao
  abstract fun games(): GameDao
  abstract fun extensions(): ExtensionDao
}
