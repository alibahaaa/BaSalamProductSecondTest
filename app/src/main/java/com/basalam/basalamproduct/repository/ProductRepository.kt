package com.basalam.basalamproduct.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.toJson
import com.apollographql.apollo.exception.ApolloException
import com.basalam.basalamproduct.GetProductsQuery
import com.basalam.basalamproduct.api.ApiClient
import com.basalam.basalamproduct.db.ProductDatabase
import com.basalam.basalamproduct.error.ErrorResponse
import com.basalam.basalamproduct.model.Product
import com.basalam.basalamproduct.model.ProductResponse
import com.basalam.basalamproduct.thread.ThreadExecutor
import com.basalam.basalamproduct.util.DataState
import com.basalam.basalamproduct.util.ResponseWrapper
import com.google.gson.Gson

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
                    println("log for graphql ${e.toString()}")
                }

                override fun onResponse(
                    response:
                    com.apollographql.apollo.api.Response<GetProductsQuery.Data>
                ) {
                    if (response.data != null) {
                        checkServerStatus(response.data!!, responseWrapper)
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

    private fun checkServerStatus(
        response: GetProductsQuery.Data,
        responseWrapper: ResponseWrapper
    ) {
        threadExecutor.execute {
            val dataResponse =
                Gson().fromJson(response.toJson(), ProductResponse::class.java)
            if (dataResponse.data != null) {
                setDataResponseFromServer(dataResponse, responseWrapper)
            } else {
                val errorResponse =
                    Gson().fromJson(response.toJson(), ErrorResponse::class.java)
                setErrorResponseFromServer(errorResponse, responseWrapper)

            }

        }
    }

    private fun setErrorResponseFromServer(
        errorResponse: ErrorResponse,
        responseWrapper: ResponseWrapper
    ) {
        responseWrapper.onResponse(
            DataState.Error(
                db.getProductDao().getAllProducts(),
                errorResponse.errors?.get(0)?.messages?.size?.get(0),
                errorResponse.errors?.get(0)?.category
            )
        )
    }

    private fun setDataResponseFromServer(
        dataResponse: ProductResponse,
        responseWrapper: ResponseWrapper
    ) {
        if (db.getProductDao()
                .getProductSize() == 0 && dataResponse.data.productSearch.products.isEmpty()
        ) {
            responseWrapper.onResponse(DataState.Empty())
        } else {
            db.getProductDao().deleteAllProducts()
            db.getProductDao()
                .insert(dataResponse.data.productSearch.products)
        }
    }
}


