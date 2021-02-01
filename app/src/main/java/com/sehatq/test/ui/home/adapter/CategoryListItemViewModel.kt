package com.sehatq.test.ui.home.adapter


import androidx.databinding.ObservableField
import com.sehatq.test.data.remote.model.Category
import com.sehatq.test.databinding.ItemCategoryBinding
import java.util.*

class CategoryListItemViewModel(itemData: Category?,
                                var actionDetail: (Category, ItemCategoryBinding) -> Unit,
                                var binding: ItemCategoryBinding) : Observable() {

    val title = ObservableField<String>()
    val image = ObservableField<String>()

    var data: Category? = itemData

    init {
        title.set(data?.name)
        image.set(data?.imageUrl)
    }

    fun goDetail() {
        data?.let { actionDetail(it, binding) }
    }

}