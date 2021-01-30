package com.sehatq.test.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sehatq.test.data.local.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * from Product")
    fun getAllProduct(): LiveData<List<Product>>

    @Query("SELECT * FROM Product WHERE id=:id")
    fun getById(id: Int): LiveData<Product>

    @get:Query("SELECT count(*) FROM Product")
    val count: Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg product: Product)
}