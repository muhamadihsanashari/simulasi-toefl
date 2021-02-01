package com.sehatq.test.ui.detailProduct

import android.content.Intent
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.sehatq.test.R
import com.sehatq.test.core.BaseViewModel
import com.sehatq.test.data.local.model.Product
import com.sehatq.test.data.repository.ProductRepositoryImpl
import com.sehatq.test.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class DetailProductViewModel(private val repository: ProductRepositoryImpl) :
    BaseViewModel() {

    var product = ObservableField<Product>()
    val loved = ObservableField<Int>()
    val textButton = ObservableField<String>()
    val shareAction = SingleLiveEvent<Intent>()
    val successInsert = SingleLiveEvent<Int>()

    fun init() {
        if (this.product.get()?.loved == 1) loved.set(R.drawable.ic_heart_filled) else loved.set(R.drawable.ic_heart)
        textButton.set("Buy - ${product.get()?.price}/Pcs")
    }

    fun changeLove() {
        if (loved.get() == R.drawable.ic_heart_filled) {
            loved.set(R.drawable.ic_heart)
        } else {
            loved.set(R.drawable.ic_heart_filled)
        }
    }

    fun shareProduct() {
        val intent = Intent(Intent.ACTION_SEND)
        val shareBody = "${product.get()?.title} Buy at SehatQ Now Only ${product.get()?.price} "
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, shareBody)
        shareAction.value = intent
    }

    fun doBuy() {
        viewModelScope.launch {
            product.get()?.let {
                val result = repository.insertProduct(it)
                if (result > 0) {
                    successInsert.value = result
                }
            }
        }
    }

}