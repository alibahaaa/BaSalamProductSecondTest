package com.basalam.basalamproduct.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.basalam.basalamproduct.api.RetrofitInstance
import com.basalam.basalamproduct.db.ProductDatabase
import com.basalam.basalamproduct.model.Product
import com.basalam.basalamproduct.model.ProductResponse
import com.basalam.basalamproduct.thread.ThreadExecutor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(
    private val db: ProductDatabase,
    private val threadExecutor: ThreadExecutor,
    private val isLoading: MutableLiveData<Boolean>
) {
    fun getProduct(query: String): LiveData<List<Product>> {
        println("check for rotate ------------------------")
        threadExecutor.execute {
            if (db.getProductDao().getProductSize() == 0)
                isLoading.postValue(true)
        }
        RetrofitInstance.api.getProduct(query).enqueue(
            object : Callback<ProductResponse> {
                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    threadExecutor.execute {
                        if (db.getProductDao().getProductSize() == 0)
                            isLoading.postValue(false)
                    }
                }

                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    threadExecutor.execute {
                        db.getProductDao().deleteAllProducts()
                        db.getProductDao().insert(response.body()?.data?.productSearch?.products!!)
                        isLoading.postValue(false)
                    }
                }
            }
        )
        return db.getProductDao().getAllProducts()
    }
}