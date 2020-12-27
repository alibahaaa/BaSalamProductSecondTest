package com.basalam.basalamproduct.api

import com.basalam.basalamproduct.model.ProductResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


/*
*********
* here we turns our HTTP req to Kotlin interface
* The endpoints are defined here
*********
 */

interface ProductApi {


    @FormUrlEncoded
    @POST("api/user")
    suspend fun getProduct(
        @Field("query") query: String
    ): Response<ProductResponse>

}