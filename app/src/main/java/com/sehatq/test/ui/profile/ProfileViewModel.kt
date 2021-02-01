package com.sehatq.test.ui.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.sehatq.test.core.BaseViewModel
import com.sehatq.test.data.local.model.Product
import com.sehatq.test.data.repository.ProductRepositoryImpl

class ProfileViewModel(private val repository: ProductRepositoryImpl) : BaseViewModel() {

    val emptyShow = ObservableField<Boolean>(true)
    val products: LiveData<List<Product>> = repository.getAll()


}