package com.basalam.basalamproduct.repository

import androidx.lifecycle.LiveData
import com.basalam.basalamproduct.api.RetrofitInstance
import com.basalam.basalamproduct.db.ProductDatabase
import com.basalam.basalamproduct.model.Product
import com.basalam.basalamproduct.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class ProductRepository(
    private val db: ProductDatabase
) {
    suspend fun getProduct(query: String): Flow<DataState<List<Product>>> = flow {
        emit(DataState.Loading)
        try {
            val networkProduct = RetrofitInstance.api.getProduct(query)
            val products = networkProduct.body()?.data?.productSearch?.products
            if (products != null) {
                for (product in products) {
                    db.getProductDao().insert(product)
                }
            }
            val cachedProducts = db.getProductDao().getAllProducts()
            emit(DataState.Success(cachedProducts))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}