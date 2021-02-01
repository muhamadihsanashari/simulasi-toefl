package com.sehatq.test.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sehatq.test.core.BaseAdapter
import com.sehatq.test.data.local.model.Product
import com.sehatq.test.databinding.ItemPurchaseBinding

class PurchaseListAdapter(
    data: ArrayList<Product>,
    var action: (Product, ItemPurchaseBinding) -> Unit
) : BaseAdapter<Product>(data) {

    private lateinit var binding: ItemPurchaseBinding


    override fun createHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemPurchaseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return GenericViewHolder(binding)
    }

    override fun bindingViewHolder(holder: GenericViewHolder, position: Int) {
        if (holder.viewDataBinding is ItemPurchaseBinding) {
            (holder.viewDataBinding as ItemPurchaseBinding).itemViewModel =
                PurchaseListItemViewModel(getItem(position), action, binding)
        }
    }


}