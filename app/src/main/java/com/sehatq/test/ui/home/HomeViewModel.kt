package com.sehatq.test.ui.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sehatq.test.core.BaseViewModel
import com.sehatq.test.data.remote.response.ResponseHome
import com.sehatq.test.data.repository.HomeRepositoryImpl
import com.sehatq.test.utils.AppResult
import com.sehatq.test.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepositoryImpl) : BaseViewModel() {

    val showLoading = ObservableBoolean()
    val homeData = MutableLiveData<ResponseHome>()
    private val showError = SingleLiveEvent<String>()
    val btnSearchEvent = SingleLiveEvent<Long>()


    fun loadData() {
        showLoading.set(true)
        viewModelScope.launch {
            val result = repository.getHome()

            showLoading.set(false)
            when (result) {
                is AppResult.Success -> {
                    homeData.value = result.successData[0]
                    showError.value = null
                }

                is AppResult.Error -> {
                    showError.value = result.exception.message
                }

            }
        }
    }

    fun navigateToSearch() {
        btnSearchEvent.value = System.currentTimeMillis()
    }
}