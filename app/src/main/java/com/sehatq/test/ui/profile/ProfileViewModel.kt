package com.sehatq.test.ui.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sehatq.test.core.BaseViewModel
import com.sehatq.test.data.local.model.Product
import com.sehatq.test.data.repository.ProductRepositoryImpl
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ProductRepositoryImpl) : BaseViewModel() {

    val emptyShow = ObservableField<Boolean>(true)

    private val _productLiveData by lazy { MutableLiveData<List<Product>>() }
    val productLiveData: LiveData<List<Product>> by lazy { _productLiveData }

    fun getProducts() {
        launch {
            val result = repository.getAll()
            if (!result.isNullOrEmpty()) run {
                _productLiveData.postValue(result)
            }
        }
    }
}