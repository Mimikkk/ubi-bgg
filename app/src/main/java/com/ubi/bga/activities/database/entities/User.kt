package com.ubi.bga.activities.database.entities

import androidx.annotation.NonNull
import androidx.room.*
import java.sql.Date

data class Owned(
  val games: Long,
  val extensions: Long,
)

@Entity
data class User(
  val name: String,
  @Embedded val owned: Owned,
  val lastSynchronized: String,
  val BggId: Long,
  @PrimaryKey(autoGenerate = true) @NonNull val id: Long = 0,
)
