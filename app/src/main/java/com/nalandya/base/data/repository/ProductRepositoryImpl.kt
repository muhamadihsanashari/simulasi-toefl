package com.nalandya.base.data.repository

import com.nalandya.base.data.local.database.dao.ProductDao
import com.nalandya.base.data.local.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ProductRepositoryImpl(private val productDao: ProductDao) : ProductRepository {

    override suspend fun insertProduct(product: Product): Int {
        var result = 0
        withContext(Dispatchers.IO) {
            try {
                result = productDao.insert(product).toInt()
            } catch (e: Exception) {
                Timber.e(e.message.toString())
            }
        }

        return result
    }

    override suspend fun getAll(): List<Product>{
        var result: List<Product>
        withContext(Dispatchers.IO) {
            result = productDao.getAllProduct()
        }
        return result
    }


}