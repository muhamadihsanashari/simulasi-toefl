package com.sehatq.test.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @field: PrimaryKey val id: Int,
    val imageUrl: String,
    val title: String,
    val description: String,
    val price: String,
    var loved: Int,
    var qty: Int = 0
)