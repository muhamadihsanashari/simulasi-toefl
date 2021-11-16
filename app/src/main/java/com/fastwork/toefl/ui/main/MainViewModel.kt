package com.fastwork.toefl.ui.main

import android.content.SharedPreferences
import com.fastwork.toefl.core.BaseViewModel
import com.fastwork.toefl.utils.POST_TEST_CHANCE_KEY
import com.fastwork.toefl.utils.PRE_TEST_CHANCE_KEY
import com.fastwork.toefl.utils.SingleLiveEvent

class MainViewModel(private val sharedPreferences: SharedPreferences) : BaseViewModel() {

    val onPracticeClicked = SingleLiveEvent<Void>()
    val onPreTestClicked = SingleLiveEvent<Void>()
    val onPostTestClicked = SingleLiveEvent<Void>()
    val onTestingClicked = SingleLiveEvent<Void>()
    val onScoreClicked = SingleLiveEvent<Void>()
    val onProfileClicked = SingleLiveEvent<Void>()
    val onClickAbout = SingleLiveEvent<Void>()

    fun onPracticeClicked() {
        onPracticeClicked.call()
    }

    fun onPreTestClicked() {
        onPreTestClicked.call()
    }

    fun onPostTestClicked() {
        onPostTestClicked.call()
    }

    fun onTestingClicked() {
        onTestingClicked.call()
    }

    fun onScoreClicked() {
        onScoreClicked.call()
    }

    fun onProfileClicked(){
        onProfileClicked.call()
    }

    fun onClickAbout(){
        onClickAbout.call()
    }

    val preTestChance = sharedPreferences.getInt(PRE_TEST_CHANCE_KEY, 0)
    val postTestChance = sharedPreferences.getInt(POST_TEST_CHANCE_KEY, 0)
}