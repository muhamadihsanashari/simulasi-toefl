package com.fastwork.toefl.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.fastwork.toefl.data.local.model.User

@Dao
interface UserDao {

    @Query("SELECT * from user where username=:username AND password=:password")
    suspend fun loginUser(username: String, password: String): User
}