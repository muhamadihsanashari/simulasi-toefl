package com.fastwork.toefl.ui.splash

import android.content.SharedPreferences
import com.fastwork.toefl.core.BaseViewModel
import com.fastwork.toefl.utils.POST_TEST_CHANCE_KEY
import com.fastwork.toefl.utils.PRE_TEST_CHANCE_KEY
import com.fastwork.toefl.utils.USER_ID_KEY

class SplashViewModel(private val sharedPreferences: SharedPreferences) : BaseViewModel() {

    val isLogin = sharedPreferences.getInt(USER_ID_KEY, 0) != 0

    fun setChancesPreAndPostTest() {
        val currentPreChance = sharedPreferences.getInt(PRE_TEST_CHANCE_KEY, 0)
        val currentPostChance = sharedPreferences.getInt(POST_TEST_CHANCE_KEY, 0)
        val preChanceCount = if (currentPreChance == 0) 3 else currentPreChance
        val postChanceCount = if (currentPostChance == 0) 3 else currentPostChance
        sharedPreferences.edit().putInt(PRE_TEST_CHANCE_KEY, preChanceCount).apply()
        sharedPreferences.edit().putInt(POST_TEST_CHANCE_KEY, postChanceCount).apply()
    }

}