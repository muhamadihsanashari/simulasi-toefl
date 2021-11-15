package com.fastwork.toefl.utils

object Validator {

    fun isUsernameValid(username: String): Boolean {
        return username.length > 5
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}