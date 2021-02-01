package com.sehatq.test.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sehatq.test.core.BaseAdapter
import com.sehatq.test.data.remote.model.Product
import com.sehatq.test.databinding.ItemProductBinding

class ProductListAdapter(
    data: ArrayList<Product>,
    var action: (Product, ItemProductBinding) -> Unit
) : BaseAdapter<Product>(data) {

    private lateinit var binding: ItemProductBinding


    override fun createHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return GenericViewHolder(binding)
    }

    override fun bindingViewHolder(holder: GenericViewHolder, position: Int) {
        if (holder.viewDataBinding is ItemProductBinding) {
            (holder.viewDataBinding as ItemProductBinding).itemViewModel =
                ProductListItemViewModel(getItem(position), action, binding)
        }
    }


}