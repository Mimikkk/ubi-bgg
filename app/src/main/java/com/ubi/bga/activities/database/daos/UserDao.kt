package com.ubi.bga.activities.database.daos

import androidx.room.*
import com.ubi.bga.activities.database.entities.User

@Dao
interface UserDao {
  @Query("select * from User")
  fun readAll(): List<User>

  @Query("select * from User where id = :id")
  fun read(id: Long): User

  @Delete
  fun remove(user: User)

  @Update
  fun update(vararg users: User)

  @Insert
  fun create(user: User): Long

  @Insert
  fun create(vararg users: User): List<Long>
}
