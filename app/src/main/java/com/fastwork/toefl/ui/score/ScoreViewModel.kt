package com.fastwork.toefl.ui.score

import com.fastwork.toefl.core.BaseViewModel
import com.fastwork.toefl.data.local.model.Score
import com.fastwork.toefl.data.local.model.ScoreType
import com.fastwork.toefl.data.repository.ScoreRepository
import com.fastwork.toefl.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class ScoreViewModel(private val scoreRepository: ScoreRepository) : BaseViewModel() {

    val successInsert = SingleLiveEvent<Int>()

    fun insertData(scoreType: ScoreType?) {
        val data = Score(
            score = scoreType?.score!!,
            category = scoreType.category,
            date = Date(),
            userId = 1
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

}