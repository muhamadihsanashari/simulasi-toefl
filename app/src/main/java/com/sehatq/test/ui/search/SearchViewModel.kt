package com.sehatq.test.ui.search

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import com.sehatq.test.core.BaseViewModel
import com.sehatq.test.data.local.model.Product

class SearchViewModel : BaseViewModel() {

    val dataProduct = MutableLiveData<List<Product>>()

    fun searchTextChange(s: Editable) {
        populateData(s.toString())
    }

    private fun populateData(textSearch: String) {
        val products = ArrayList<Product>()
        for (i in 0..10) {
            products.add(
                Product(
                    i,
                    i.toString(),
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/NES-Console-Set.jpg/430px-NES-Console-Set.jpg",
                    "$textSearch Nintendo S",
                    "The Nintendo Switch was released on March 3, 2017",
                    "$60",
                    1
                )
            )
        }
        dataProduct.value = products.toList()
    }
}