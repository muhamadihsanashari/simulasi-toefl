package com.fastwork.toefl.ui.postAndPreTest

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fastwork.toefl.core.BaseViewModel
import com.fastwork.toefl.data.local.model.Listening
import com.fastwork.toefl.data.local.model.Reading
import com.fastwork.toefl.data.local.model.Structure
import com.fastwork.toefl.data.repository.PracticeRepository
import kotlinx.coroutines.launch

class PreAndPostTestViewModel(
    private val sharedPreferences: SharedPreferences,
    private val practiceRepository: PracticeRepository
) : BaseViewModel() {

    private val _readLiveData by lazy { MutableLiveData<List<Reading>>() }
    val readLiveData: LiveData<List<Reading>> by lazy { _readLiveData }

    private val _structureLiveData by lazy { MutableLiveData<List<Structure>>() }
    val structureLiveData: LiveData<List<Structure>> by lazy { _structureLiveData }

    private val _listeningLiveData by lazy { MutableLiveData<List<Listening>>() }
    val listeningLiveData: LiveData<List<Listening>> by lazy { _listeningLiveData }

    fun getReadingQuestion(difficulty: String) {
        launch {
            val result = practiceRepository.getReadingData(difficulty)
            if (result.isNotEmpty()) run { _readLiveData.postValue(result) }
        }
    }

    fun getStructureQuestion(category: String) {
        launch {
            val result = practiceRepository.getStructureData(category)
            if (result.isNotEmpty()) run { _structureLiveData.postValue(result) }
        }
    }

    fun getListeningQuestion(category: String) {
        launch {
            val result = practiceRepository.getListeningData(category)
            if (result.isNotEmpty()) run { _listeningLiveData.postValue(result) }
        }
    }
}