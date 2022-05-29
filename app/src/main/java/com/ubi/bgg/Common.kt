package com.ubi.bgg

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import com.ubi.bgg.database.ApplicationDB

@SuppressLint("StaticFieldLeak")
internal object Common {
  @Volatile
  lateinit var Context: Context

  @Volatile
  lateinit var Database: ApplicationDB

  fun initialize(context: Context) {
    Context = context
    Database = Room.databaseBuilder(
      Context,
      ApplicationDB::class.java,
      "users"
    ).allowMainThreadQueries().build()
  }
}
