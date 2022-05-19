package com.ubi.bga.activities.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Extension(
  val primaryId: Long,
  val title: String,
  val thumbnail: String?,
  val description: String,
  val published: Int,
  val BGGId: Long,
  @PrimaryKey(autoGenerate = true) @NonNull val id: Long = 0,
)
