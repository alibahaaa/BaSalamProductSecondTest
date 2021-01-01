package com.basalam.basalamproduct.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.basalam.basalamproduct.api.RetrofitInstance
import com.basalam.basalamproduct.db.ProductDatabase
import com.basalam.basalamproduct.model.Product
import com.basalam.basalamproduct.model.ProductResponse
import com.basalam.basalamproduct.thread.ThreadExecutor
import com.basalam.basalamproduct.util.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(
    private val db: ProductDatabase,
    private val threadExecutor: ThreadExecutor
) {
    val productRes: MutableLiveData<Resource<LiveData<List<Product>>>> = MutableLiveData()
    fun getProduct(query: String): MutableLiveData<Resource<LiveData<List<Product>>>> {
        println("rotate log for test")
        threadExecutor.execute {
            productRes.postValue(Resource.Success(db.getProductDao().getAllProducts()))
            if (db.getProductDao().getProductSize() == 0) {
                productRes.postValue(Resource.Loading())
            }
        }

        RetrofitInstance.api.getProduct(query).enqueue(
            object : Callback<ProductResponse> {
                override fun onFailure(
                    call: Call<ProductResponse>,
                    t: Throwable
                ) {
                    threadExecutor.execute {
                        productRes.postValue(
                            Resource.Error(
                                t.message!!,
                                db.getProductDao().getAllProducts()
                            )
                        )
                    }
                }

                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    threadExecutor.execute {
                        if (response.code() == 200) {
                            println(response.body()?.data?.productSearch?.products!!.size.toString())
                            if (db.getProductDao().getProductSize() == 0) {
                                if (response.body()!!.data.productSearch.products.isEmpty()) {
                                    productRes.postValue(Resource.Empty())
                                } else {
                                    productRes.postValue(
                                        Resource.Success(
                                            db.getProductDao().getAllProducts()
                                        )
                                    )
                                }
                            }
                            db.getProductDao().deleteAllProducts()
                            db.getProductDao()
                                .insert(response.body()?.data?.productSearch?.products!!)
                        } else {
                            productRes.postValue(
                                Resource.Error(
                                    response.code().toString(),
                                    db.getProductDao().getAllProducts()
                                )
                            )
                        }
                    }
                }
            }
        )
        return productRes
    }
}