package com.fastwork.toefl.ui.splash

import android.content.SharedPreferences
import com.fastwork.toefl.core.BaseViewModel
import com.fastwork.toefl.utils.USER_ID_KEY

class SplashViewModel(private val sharedPreferences: SharedPreferences) : BaseViewModel() {

    val isLogin = sharedPreferences.getInt(USER_ID_KEY, 0) != 0
}