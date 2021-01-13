package com.basalam.basalamproduct.di

import android.content.Context
import androidx.room.Room
import com.basalam.basalamproduct.db.ProductDao
import com.basalam.basalamproduct.db.ProductDatabase
import dagger.Module
import dagger.Provides


@Module
class DataBaseModule {
    @Provides
    fun provideDatabase(context: Context): ProductDatabase {
        return Room.databaseBuilder(
            context,
            ProductDatabase::class.java,
            "product_db.db"
        ).build()
    }

    @Provides
    internal fun provideProductDao(productDatabase: ProductDatabase): ProductDao {
        return productDatabase.getProductDao()
    }
}