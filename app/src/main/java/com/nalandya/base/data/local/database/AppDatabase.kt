package com.nalandya.base.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nalandya.base.data.local.database.dao.ProductDao
import com.nalandya.base.data.local.model.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun productDao(): ProductDao
}