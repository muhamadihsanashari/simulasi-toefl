package com.fastwork.toefl.ui.score.adapter

import androidx.databinding.ObservableField
import com.fastwork.toefl.data.local.model.Score
import com.fastwork.toefl.databinding.LayoutScoreBinding
import java.text.SimpleDateFormat
import java.util.*

class ItemScoreViewModel(itemData: Score?, var binding: LayoutScoreBinding) : Observable() {

    val score = ObservableField<String>()
    val date = ObservableField<String>()

    init {
        score.set(itemData?.score.toString())
        val date = SimpleDateFormat("dd/MM/yyyy").format(itemData?.date!!)
        this.date.set(date)
    }

}