package com.basalam.basalamproduct.repository

import androidx.room.Query
import com.basalam.basalamproduct.api.RetrofitInstance
import com.basalam.basalamproduct.db.ProductDatabase

/*
*********
* Here we Create our Repository for provides method between view model and retrofit request
*********
 */

class ProductRepository(val db: ProductDatabase) {

    suspend fun getProduct(query: String) = RetrofitInstance.api.getProduct(query)

}