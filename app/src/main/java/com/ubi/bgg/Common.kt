package com.ubi.bgg

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.ubi.bgg.database.ApplicationDB
import org.jetbrains.annotations.PropertyKey

@SuppressLint("StaticFieldLeak")
internal object Common {
  @Volatile
  lateinit var Context: Context

  @Volatile
  lateinit var Database: ApplicationDB

  val Preferences: SharedPreferences
    get() = Context.getSharedPreferences("Preferences", MODE_PRIVATE)


  fun initialize(context: Context) {
    Context = context
    Database = Room.databaseBuilder(
      Context,
      ApplicationDB::class.java,
      "users"
    ).allowMainThreadQueries().build()
  }
}
