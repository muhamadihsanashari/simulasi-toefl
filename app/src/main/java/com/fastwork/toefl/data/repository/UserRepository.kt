package com.fastwork.toefl.data.repository

import com.fastwork.toefl.data.local.model.User

interface UserRepository {
    suspend fun login(username: String, password: String) : User?
}