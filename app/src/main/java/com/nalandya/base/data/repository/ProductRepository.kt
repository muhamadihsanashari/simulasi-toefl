package com.nalandya.base.data.repository

import com.nalandya.base.data.local.model.Product

interface ProductRepository {
    suspend fun insertProduct(product: Product): Int
    suspend fun getAll(): List<Product>
}