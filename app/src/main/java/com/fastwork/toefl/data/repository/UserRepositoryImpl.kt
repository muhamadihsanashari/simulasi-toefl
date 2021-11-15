package com.fastwork.toefl.data.repository

import com.fastwork.toefl.data.local.database.dao.UserDao
import com.fastwork.toefl.data.local.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override suspend fun login(username: String, password: String): User {
        var user: User = User()
        withContext(Dispatchers.IO) {
            try {
                user = userDao.loginUser(username, password)
            } catch (e: Exception) {
                Timber.e(e.message.toString())
            }
        }
        return user
    }


}