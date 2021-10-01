package com.fastwork.toefl.utils

import android.util.Patterns

object Validator {

    fun isEmailValid(username: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}