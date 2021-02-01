package com.sehatq.test.data.repository

import androidx.lifecycle.LiveData
import com.sehatq.test.data.local.database.dao.ProductDao
import com.sehatq.test.data.local.model.Product
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

    override fun getAll(): LiveData<List<Product>> {
        return productDao.getAllProduct()
    }


}