package com.fastwork.toefl.ui.score.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastwork.toefl.core.BaseAdapter
import com.fastwork.toefl.data.local.model.Score
import com.fastwork.toefl.databinding.LayoutScoreBinding

class AdapterScore(val data: ArrayList<Score>) : BaseAdapter<Score>(data) {

    private lateinit var binding: LayoutScoreBinding

    override fun createHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = LayoutScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenericViewHolder(binding)
    }

    override fun bindingViewHolder(holder: GenericViewHolder, position: Int) {
        if (holder.viewDataBinding is LayoutScoreBinding) {
            (holder.viewDataBinding as LayoutScoreBinding).viewModel =
                ItemScoreViewModel(getItem(position), binding)
        }
    }
}