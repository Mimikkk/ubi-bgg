package com.ubi.bgg.activities.database.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(
  val title: String,
  val thumbnail: String?,
  val description: String,
  val published: Int,
  val BGGId: Long,
  @PrimaryKey(autoGenerate = true) @NonNull val id: Long = 0,
)
