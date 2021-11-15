package com.fastwork.toefl.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fastwork.toefl.data.local.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<User>)

    @Query("SELECT * from user where username=:username AND password=:password")
    suspend fun loginUser(username: String, password: String): User

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>
}