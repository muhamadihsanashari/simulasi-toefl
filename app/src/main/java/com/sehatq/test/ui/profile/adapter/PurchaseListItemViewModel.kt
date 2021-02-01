package com.sehatq.test.ui.profile.adapter

import androidx.core.view.ViewCompat
import androidx.databinding.ObservableField
import com.sehatq.test.R
import com.sehatq.test.data.local.model.Product
import com.sehatq.test.databinding.ItemPurchaseBinding
import java.util.*

class PurchaseListItemViewModel(
    itemData: Product?,
    var actionDetail: (Product, ItemPurchaseBinding) -> Unit,
    var binding: ItemPurchaseBinding
) : Observable() {

    val title = ObservableField<String>()
    val image = ObservableField<String>()
    val description = ObservableField<String>()
    val price = ObservableField<String>()
    val loved = ObservableField<Int>()

    var data: Product? = itemData

    init {
        title.set(data?.title)
        image.set(data?.imageUrl)
        description.set(data?.description)
        price.set(data?.price)
        if (data?.loved == 1) loved.set(R.drawable.ic_heart_filled) else loved.set(R.drawable.ic_heart)
    }

    fun goDetail() {
        ViewCompat.setTransitionName(binding.ivImage, "image")
        ViewCompat.setTransitionName(binding.ivLove, "love")
        ViewCompat.setTransitionName(binding.tvTitle, "title")
        ViewCompat.setTransitionName(binding.tvDescription, "desc")
        ViewCompat.setTransitionName(binding.tvPrice, "price")
        with(binding) {
            executePendingBindings()
        }
        data?.let { actionDetail(it, binding) }
    }

}