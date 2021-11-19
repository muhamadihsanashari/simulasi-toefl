package com.fastwork.toefl.ui.score.adapter

import android.annotation.SuppressLint
import androidx.databinding.ObservableField
import com.fastwork.toefl.data.local.model.Score
import com.fastwork.toefl.databinding.LayoutScoreAdminBinding
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class ItemScoreAdmin(itemData: Score?, var binding: LayoutScoreAdminBinding) : Observable() {

    val name = ObservableField<String>()
    val score = ObservableField<String>()
    val date = ObservableField<String>()

    init {
        name.set(itemData?.userName)
        score.set(itemData?.score.toString())
        val date = SimpleDateFormat("dd/MM/yyyy").format(itemData?.date!!)
        this.date.set(date)
    }
}