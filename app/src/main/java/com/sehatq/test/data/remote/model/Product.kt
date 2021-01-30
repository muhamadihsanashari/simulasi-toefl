package com.sehatq.test.data.remote.model

import androidx.room.PrimaryKey

data class Product(
    val id: String,
    val imageUrl: String,
    val title: String,
    val description: String,
    val price: String,
    var loved: Int,
    var qty: Int = 0
)