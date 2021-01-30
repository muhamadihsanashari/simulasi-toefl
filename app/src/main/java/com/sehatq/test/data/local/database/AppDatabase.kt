package com.sehatq.test.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sehatq.test.data.local.database.dao.ProductDao
import com.sehatq.test.data.local.model.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun productDao(): ProductDao
}