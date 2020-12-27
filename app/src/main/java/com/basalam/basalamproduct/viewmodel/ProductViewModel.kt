package com.basalam.basalamproduct.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.basalam.basalamproduct.model.ProductResponse
import com.basalam.basalamproduct.repository.ProductRepository
import com.basalam.basalamproduct.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


/*
*********
* hee we create The ProductViewModel class that designed to store and manage UI-related data in a lifecycle conscious way.
* The ViewModel class allows data to survive configuration changes such as screen rotations.
*********
 */


class ProductViewModel(

    val productRepository: ProductRepository

) : ViewModel() {

    val products: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()


    init {
        getProducts("{productSearch(size: 20) {products {id name photo(size: LARGE) { url } vendor { name } weight price rating { rating count: signals } } } }")
    }

    fun getProducts(query: String) = viewModelScope.launch {

        products.postValue(Resource.Loading())
        val response = productRepository.getProduct(query)
        products.postValue(handleProductResponse(response))
    }


    private fun handleProductResponse(response: Response<ProductResponse>) : Resource<ProductResponse>{
        if(response.isSuccessful){
            response.body()?.let {resultResponse->
                return Resource.Success(resultResponse)
            }

        }
        return Resource.Error(response.message())

    }





}