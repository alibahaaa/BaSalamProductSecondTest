package com.basalam.basalamproduct.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.basalam.basalamproduct.model.Product


/*
*********
* Here we Create our Product Dao to provides the methods that the rest of the app uses to interact with data in the "product_table" table.
*********
 */

@Dao
interface ProductDao {

    //insert data to our product , if we find same data , we replace it with old one !
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product): Long

    //select all data from product table
    @Query("SELECT * FROM product_table")
    fun getAllProducts(): LiveData<List<Product>>

    // we don't use it rn.
    @Delete
    suspend fun deleteProducts(product: Product)

}