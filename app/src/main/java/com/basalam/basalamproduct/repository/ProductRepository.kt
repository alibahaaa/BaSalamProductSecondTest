package com.basalam.basalamproduct.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.toJson
import com.apollographql.apollo.exception.ApolloException
import com.basalam.basalamproduct.GetProductsQuery
import com.basalam.basalamproduct.api.ApiClient
import com.basalam.basalamproduct.api.ApiMapper
import com.basalam.basalamproduct.db.ProductDatabase
import com.basalam.basalamproduct.error.ErrorResponse
import com.basalam.basalamproduct.model.Product
import com.basalam.basalamproduct.model.ProductResponse
import com.basalam.basalamproduct.thread.ThreadExecutor
import com.basalam.basalamproduct.util.DataState
import com.basalam.basalamproduct.util.ResponseWrapper
import com.google.gson.Gson

@Suppress("UNCHECKED_CAST")
class ProductRepository(
    private val db: ProductDatabase,
    private val threadExecutor: ThreadExecutor
) {
    lateinit var res: DataState<LiveData<List<Product>>>
    fun getProduct(
        query: Int,
        responseWrapper: ResponseWrapper
    ): DataState<LiveData<List<Product>>> {
        println("rotate log")
        setSuccessWrapper(responseWrapper)
        getDataFromServer(query, responseWrapper)
        return res
    }

    private fun setSuccessWrapper(responseWrapper: ResponseWrapper) {
        threadExecutor.execute {
            res = DataState.Success(db.getProductDao().getAllProducts())
            responseWrapper.onResponse(
                DataState.Success(
                    db.getProductDao().getAllProducts()
                )
            )
        }
    }

    private fun getDataFromServer(query: Int, responseWrapper: ResponseWrapper) {
        ApiClient.apolloClient.query(GetProductsQuery(query)).watcher()
            .enqueueAndWatch(object :
                ApolloCall.Callback<GetProductsQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    setUnKnownError(
                        responseWrapper,
                        "اتصال خود را به اینترنت چک کنید"
                    )
                    println("log for graphql $e")
                }

                override fun onResponse(
                    response:
                    com.apollographql.apollo.api.Response<GetProductsQuery.Data>
                ) {
                    if (response.data != null) {
                        val res: List<Product> =
                            ApiMapper().mapFromEntities(
                                response.data?.productSearch?.products
                                        as List<GetProductsQuery.Product>
                            )
                        checkSuccessStatus(responseWrapper, res)
                    } else {
                        setUnKnownError(
                            responseWrapper,
                            "خطایی سمت سرور رخ داده است"
                        )
                    }
                }
            })
    }

    private fun setUnKnownError(responseWrapper: ResponseWrapper, message: String) {
        threadExecutor.execute {
            responseWrapper.onResponse(
                DataState.Error(
                    db.getProductDao().getAllProducts(),
                    message,
                    "unKnown"
                )
            )
        }
    }

    private fun checkSuccessStatus(
        responseWrapper: ResponseWrapper,
        response: List<Product>
    ) {
        threadExecutor.execute {
            if (db.getProductDao()
                    .getProductSize() == 0 && response.isEmpty()
            ) {
                responseWrapper.onResponse(DataState.Empty())
            } else {
                db.getProductDao().deleteAllProducts()
                db.getProductDao()
                    .insert(response)
            }
        }
    }
}





