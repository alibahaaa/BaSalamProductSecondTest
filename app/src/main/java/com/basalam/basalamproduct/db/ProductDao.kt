package com.basalam.basalamproduct.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.basalam.basalamproduct.model.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productEntity: Product): Long

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): List<Product>

    @Delete
    suspend fun deleteProducts(product: Product)
}