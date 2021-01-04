package com.basalam.basalamproduct.repository

import androidx.lifecycle.LiveData
import com.basalam.basalamproduct.api.RetrofitInstance
import com.basalam.basalamproduct.db.ProductDatabase
import com.basalam.basalamproduct.error.ErrorResponse
import com.basalam.basalamproduct.model.Data
import com.basalam.basalamproduct.model.Product
import com.basalam.basalamproduct.model.ProductResponse
import com.basalam.basalamproduct.thread.ThreadExecutor
import com.basalam.basalamproduct.util.DataState
import com.basalam.basalamproduct.util.ResponseWrapper
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(
    private val db: ProductDatabase,
    private val threadExecutor: ThreadExecutor

) {
    lateinit var res: DataState<LiveData<List<Product>>>

    fun getProduct(
        query: String,
        responseWrapper: ResponseWrapper
    ): DataState<LiveData<List<Product>>> {
        println("rotate log")
        threadExecutor.execute {
            res = DataState.Success(db.getProductDao().getAllProducts())
            responseWrapper.onResponse(
                DataState.Success(
                    db.getProductDao().getAllProducts()
                )
            )
        }
        RetrofitInstance.api.getProduct(query).enqueue(
            object : Callback<JsonObject> {
                override fun onFailure(
                    call: Call<JsonObject>,
                    t: Throwable
                ) {
                    threadExecutor.execute {
                        responseWrapper.onResponse(
                            DataState.Error(
                                db.getProductDao().getAllProducts(),
                                "اتصال خود را به اینترنت چک کنید",
                                "unKnown"
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
                                if (db.getProductDao()
                                        .getProductSize() == 0 && dataResponse.data.productSearch.products.isEmpty()
                                ) {
                                    responseWrapper.onResponse(DataState.Empty())
                                } else {
                                    db.getProductDao().deleteAllProducts()
                                    db.getProductDao()
                                        .insert(dataResponse.data.productSearch.products)
//                                    responseWrapper.onResponse(
//                                        DataState.Success(
//                                            db.getProductDao().getAllProducts()
//                                        )
//                                    )
                                }
                            } else {
                                val errorResponse =
                                    Gson().fromJson(response.body(), ErrorResponse::class.java)
                                responseWrapper.onResponse(
                                    DataState.Error(
                                        db.getProductDao().getAllProducts(),
                                        errorResponse.errors?.get(0)?.messages?.size?.get(0),
                                        errorResponse.errors?.get(0)?.category
                                    )
                                )
                            }
                        }
                    }
                }
            }
        )
        return res
    }
}