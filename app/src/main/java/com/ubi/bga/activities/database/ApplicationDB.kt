package com.ubi.bga.activities.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ubi.bga.activities.database.daos.ExtensionDao
import com.ubi.bga.activities.database.daos.GameDao
import com.ubi.bga.activities.database.daos.RankDao
import com.ubi.bga.activities.database.entities.Extension
import com.ubi.bga.activities.database.entities.Game
import com.ubi.bga.activities.database.entities.Rank

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
