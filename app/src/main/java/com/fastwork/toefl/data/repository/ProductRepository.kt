package com.fastwork.toefl.data.repository

import com.fastwork.toefl.data.local.model.Product

interface ProductRepository {
    suspend fun insertProduct(product: Product): Int
    suspend fun getAll(): List<Product>
}