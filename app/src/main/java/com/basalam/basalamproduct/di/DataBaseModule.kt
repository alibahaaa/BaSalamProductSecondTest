package com.basalam.basalamproduct.di

import android.content.Context
import androidx.room.Room
import com.basalam.basalamproduct.db.ProductDao
import com.basalam.basalamproduct.db.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DataBaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ProductDatabase {
        return Room.databaseBuilder(
            context,
            ProductDatabase::class.java,
            "product_db.db"
        ).build()
    }

    @Provides
    @Singleton
    internal fun provideProductDao(productDatabase: ProductDatabase): ProductDao {
        return productDatabase.getProductDao()
    }
}