package com.sehatq.test.data.repository

import androidx.lifecycle.LiveData
import com.sehatq.test.data.local.model.Product

interface ProductRepository {
    suspend fun insertProduct(product: Product): Int
    suspend fun getAll(): List<Product>
}