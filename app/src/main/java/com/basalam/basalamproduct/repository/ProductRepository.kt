package com.basalam.basalamproduct.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.basalam.basalamproduct.api.RetrofitInstance
import com.basalam.basalamproduct.db.ProductDatabase
import com.basalam.basalamproduct.error.ErrorResponse
import com.basalam.basalamproduct.model.Product
import com.basalam.basalamproduct.model.ProductResponse
import com.basalam.basalamproduct.thread.ThreadExecutor
import com.basalam.basalamproduct.util.Resource
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(
    private val db: ProductDatabase,
    private val threadExecutor: ThreadExecutor
) {
    val productRes: MutableLiveData<Resource<LiveData<List<Product>>>> = MutableLiveData()
    fun getProduct(query: String): MutableLiveData<Resource<LiveData<List<Product>>>> {
        println("rotate log")
        threadExecutor.execute {
            productRes.postValue(Resource.Success(db.getProductDao().getAllProducts()))
            if (db.getProductDao().getProductSize() == 0) {
                productRes.postValue(Resource.Loading())
            }
        }
        RetrofitInstance.api.getProduct(query).enqueue(
            object : Callback<JsonObject> {
                override fun onFailure(
                    call: Call<JsonObject>,
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
                    call: Call<JsonObject>,
                    response: Response<JsonObject>
                ) {
                    threadExecutor.execute {
                        if (response.code() == 200) {
                            val dataResponse =
                                Gson().fromJson(response.body(), ProductResponse::class.java)
                            if (dataResponse.data != null) {
                                if (db.getProductDao().getProductSize() == 0) {
                                    if (dataResponse.data.productSearch.products.isEmpty()) {
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
                                    .insert(dataResponse.data.productSearch.products)
                            } else {
                                val errorResponse =
                                    Gson().fromJson(response.body(), ErrorResponse::class.java)
                                println("log ${errorResponse.errors?.get(0)?.messages?.size}")
                                productRes.postValue(
                                    errorResponse.errors?.get(0)?.messages?.size?.get(0)?.let {
                                        Resource.Error(
                                            it,
                                            db.getProductDao().getAllProducts()
                                        )
                                    }
                                )
                            }
                        }
//                        if(response.code() == 203) {}//203 Non-Authoritative Information
                        if (response.code() in 400..499) { //client error
//                            val errorResponse: ErrorResponse = ErrorUtils.parseError(response)
//                            println(" log for test ${errorResponse.errors?.get(0)?.messages?.size?.get(0)}")
                            productRes.postValue(
                                Resource.Error(
                                    response.code().toString(),
                                    db.getProductDao().getAllProducts()
                                )
                            )
                        }
                        if (response.code() in 500..599) { //server error
//                            val errorResponse: ErrorResponse = ErrorUtils.parseError(response)
//                            println(" log for test ${errorResponse.errors?.get(0)?.messages?.size?.get(0)}")
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