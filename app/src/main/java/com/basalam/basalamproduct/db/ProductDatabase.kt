package com.basalam.basalamproduct.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.basalam.basalamproduct.model.Product


@Database(entities = [Product::class], version = 2)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
}