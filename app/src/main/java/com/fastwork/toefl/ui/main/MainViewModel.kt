package com.fastwork.toefl.ui.main

import com.fastwork.toefl.core.BaseViewModel
import com.fastwork.toefl.data.repository.HomeRepository
import kotlinx.coroutines.launch

class MainViewModel(private val homeRepository: HomeRepository) : BaseViewModel() {

    fun getListParagraph() {
        launch {
            homeRepository.getListParagraph()
        }
    }
}