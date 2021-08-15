package com.nalandya.base.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) val productId: Int,
    val id: String,
    val imageUrl: String,
    val title: String,
    val description: String,
    val price: String,
    var loved: Int,
    var qty: Int = 0
) : Serializable