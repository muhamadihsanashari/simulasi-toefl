package com.fastwork.toefl.ui.score

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fastwork.toefl.core.BaseViewModel
import com.fastwork.toefl.data.local.model.Score
import com.fastwork.toefl.data.local.model.ScoreType
import com.fastwork.toefl.data.repository.ScoreRepository
import com.fastwork.toefl.ui.postAndPreTest.DirectionFragment
import com.fastwork.toefl.utils.POST_TEST_CHANCE_KEY
import com.fastwork.toefl.utils.PRE_TEST_CHANCE_KEY
import com.fastwork.toefl.utils.SingleLiveEvent
import com.fastwork.toefl.utils.USER_ID_KEY
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class ScoreViewModel(
    private val scoreRepository: ScoreRepository,
    private val sharedPreferences: SharedPreferences
) : BaseViewModel() {

    val successInsert = SingleLiveEvent<Int>()

    private val _scoreListLiveData by lazy { MutableLiveData<List<Score>>() }
    val scoreListLiveData: LiveData<List<Score>> by lazy { _scoreListLiveData }

    fun insertData(scoreType: ScoreType?) {
        if (scoreType?.category == DirectionFragment.PRETEST_TYPE) {
            val currentPreChance = sharedPreferences.getInt(PRE_TEST_CHANCE_KEY, 0)
            sharedPreferences.edit().putInt(PRE_TEST_CHANCE_KEY, currentPreChance - 1).apply()
        } else if (scoreType?.category == DirectionFragment.POSTTEST_TYPE) {
            val currentPostChance = sharedPreferences.getInt(POST_TEST_CHANCE_KEY, 0)
            sharedPreferences.edit().putInt(POST_TEST_CHANCE_KEY, currentPostChance - 1).apply()
        }
        val data = Score(
            score = scoreType?.score!!,
            category = scoreType.category!!,
            date = Date(),
            userId = sharedPreferences.getInt(USER_ID_KEY, 0)
        )
        launch {
            try {
                val result = scoreRepository.insertScore(
                    data
                )
                if (result > 0) {
                    successInsert.value = result
                }
            } catch (e: Exception) {
                Timber.e(e.message.toString())
            }
        }
    }

    fun getScores(category: String?) {
        launch {
            try {
                val result =
                    scoreRepository.getAllScores(
                        sharedPreferences.getInt(USER_ID_KEY, 0),
                        category!!
                    )
                if (result.isNotEmpty()) {
                    _scoreListLiveData.postValue(result)
                }
            } catch (e: Exception) {
                Timber.e(e.message.toString())
            }
        }
    }


}