package com.basalam.basalamproduct.error

import com.basalam.basalamproduct.api.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

object ErrorUtils {
    fun parseError(response: Response<*>): ErrorResponse? {
        val converter: Converter<ResponseBody, ErrorResponse> =
            RetrofitInstance.retrofit.responseBodyConverter(
                ErrorResponse::class.java,
                arrayOfNulls<Annotation>(0)
            )
        val error: ErrorResponse?
        error = try {
            println("log try error happen")
            converter.convert(response.errorBody())
        } catch (e: IOException) {
            println("log catch error happen")
            return ErrorResponse()
        }
        return error
    }
}