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

  private val Preferences: SharedPreferences
    get() = Context.getSharedPreferences("Preferences", MODE_PRIVATE)

  @Suppress("UNCHECKED_CAST")
  fun <T> get(name: String, or: T? = null): T? =
    if (contains(name).not()) or else when (or) {
      is Int -> Preferences.getInt(name, or) as T
      is Long -> Preferences.getLong(name, or) as T
      is Float -> Preferences.getFloat(name, or) as T
      is Boolean -> Preferences.getBoolean(name, or) as T
      is String -> Preferences.getString(name, or) as T
      else -> throw IllegalArgumentException("Unsupported type")
    }

  fun <T> set(name: String, value: T) = Preferences.edit().let {
    when (value) {
      is Int -> it.putInt(name, value)
      is Long -> it.putLong(name, value)
      is Float -> it.putFloat(name, value)
      is Boolean -> it.putBoolean(name, value)
      is String -> it.putString(name, value)
      else -> throw IllegalArgumentException("Unsupported type")
    }
  }.apply()

  fun contains(name: String) = Preferences.contains(name)

  fun remove(name: String) = Preferences.edit().remove(name).apply()

  fun clear() = Preferences.edit().clear().apply()

  fun initialize(context: Context) {
    Context = context
    Database = Room.databaseBuilder(
      Context,
      ApplicationDB::class.java,
      "users"
    ).allowMainThreadQueries().build()
  }
}
