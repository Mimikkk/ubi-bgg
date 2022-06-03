package com.ubi.bgg.database.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rank(
  val position: Int,
  val score: Double,
  val date: String,
  val gameId: Long,
  @PrimaryKey(autoGenerate = true) @NonNull val id: Long = 0,
)
