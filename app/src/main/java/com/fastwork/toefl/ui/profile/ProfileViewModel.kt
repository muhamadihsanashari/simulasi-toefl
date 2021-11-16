package com.fastwork.toefl.ui.profile

import android.content.SharedPreferences
import com.fastwork.toefl.core.BaseViewModel
import com.fastwork.toefl.utils.SingleLiveEvent

class ProfileViewModel(private val sharedPreferences: SharedPreferences) : BaseViewModel() {

    val logoutEvent = SingleLiveEvent<Void>()

    fun logout() {
        sharedPreferences.edit().clear().apply()
        logoutEvent.call()
    }
}