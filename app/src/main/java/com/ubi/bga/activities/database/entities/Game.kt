package com.ubi.bga.activities.database.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(
  @PrimaryKey(autoGenerate = true) @NonNull val id: Long = 0,
)
