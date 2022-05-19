package com.ubi.bga.activities.database

import androidx.room.*
import com.ubi.bga.activities.database.daos.*
import com.ubi.bga.activities.database.entities.*

@Database(
  entities = [Game::class, Extension::class, Rank::class, User::class],
  version = 1,
  exportSchema = false
)
abstract class ApplicationDB : RoomDatabase() {
  abstract fun ranks(): RankDao
  abstract fun games(): GameDao
  abstract fun extensions(): ExtensionDao
  abstract fun users(): UserDao
}
