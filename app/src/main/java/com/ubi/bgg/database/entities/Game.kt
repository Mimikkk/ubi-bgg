package com.ubi.bgg.database.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(
  val title: String,
  val thumbnail: String?,
  val published: Int,
  val BGGId: Long,
  @PrimaryKey(autoGenerate = true) @NonNull val id: Long = 0,
)
