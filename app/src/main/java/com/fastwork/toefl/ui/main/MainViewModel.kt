package com.fastwork.toefl.ui.main

import com.fastwork.toefl.core.BaseViewModel
import com.fastwork.toefl.data.repository.HomeRepository
import com.fastwork.toefl.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class MainViewModel(private val homeRepository: HomeRepository) : BaseViewModel() {

    val onPracticeClicked = SingleLiveEvent<Void>()

    fun getListParagraph() {
        launch {
            homeRepository.getListParagraph()
        }
    }

    fun onPracticeClicked(){
        onPracticeClicked.call()
    }
}