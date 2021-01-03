package com.basalam.basalamproduct.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ProductApi {
    @FormUrlEncoded
    @POST("api/user")
    fun getProduct(
        @Field("query") query: String
    ): Call<JsonObject>
}