package com.basalam.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.basalam.data.entities.Product

@Database(entities = [Product::class], version = 2)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
}