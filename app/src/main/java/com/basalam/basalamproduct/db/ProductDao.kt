package com.basalam.basalamproduct.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.basalam.basalamproduct.model.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: List<Product>)

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("DELETE FROM product_table")
    fun deleteAllProducts()

    @Query("SELECT COUNT(*) FROM product_table")
    fun getProductSize(): Int
}