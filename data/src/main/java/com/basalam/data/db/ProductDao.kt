package com.basalam.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.basalam.data.entities.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: List<Product>)

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): Flow<List<Product>>

    @Query("DELETE FROM product_table")
    suspend fun deleteAllProducts()

    @Query("SELECT COUNT(*) FROM product_table")
    suspend fun getProductSize(): Int
}