package com.fastwork.toefl.ui.practice.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fastwork.toefl.core.BaseViewModel
import com.fastwork.toefl.data.local.model.ParagraphAndReading
import com.fastwork.toefl.data.local.model.ParagraphReading
import com.fastwork.toefl.data.repository.PracticeRepository
import kotlinx.coroutines.launch

class PracticeTestViewModel(private val practiceRepository: PracticeRepository) : BaseViewModel() {

    private val _readLiveData by lazy { MutableLiveData<List<ParagraphAndReading>>() }
    val readLiveData: LiveData<List<ParagraphAndReading>> by lazy { _readLiveData }

    fun getReadingQuestion(difficulty: String) {
        launch {
            val result = practiceRepository.getReadingData(difficulty)
            if (result.isNotEmpty()) run { _readLiveData.postValue(result) }
        }

    }
}