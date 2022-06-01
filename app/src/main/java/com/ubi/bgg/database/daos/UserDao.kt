package com.ubi.bgg.activities.database.daos

import androidx.room.*
import com.ubi.bgg.activities.database.entities.User

@Dao
interface UserDao {
  @Query("select * from User")
  fun read(): List<User>

  @Delete
  fun remove(user: User)

  @Update
  fun update(vararg users: User)

  @Insert
  fun create(user: User): Long
}
