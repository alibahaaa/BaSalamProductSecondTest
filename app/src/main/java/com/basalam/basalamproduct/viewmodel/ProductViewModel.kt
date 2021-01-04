package com.basalam.basalamproduct.viewmodel

import androidx.lifecycle.*
import com.basalam.basalamproduct.model.Product
import com.basalam.basalamproduct.repository.ProductRepository
import com.basalam.basalamproduct.util.DataState
import com.basalam.basalamproduct.util.Resource
import com.basalam.basalamproduct.util.ResponseWrapper

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val query: String
) : ViewModel() {
    val productData: MutableLiveData<Resource<LiveData<List<Product>>>> = MutableLiveData()

    init {
        productRepository.getProduct(query, object : ResponseWrapper {
            override fun onResponse(response: DataState<LiveData<List<Product>>>) {
                when (response) {
                    is DataState.Success -> {
                        productData.postValue(Resource.Success(response.data!!))
                    }
                    is DataState.Error -> {
                        when {
                            response.categoryError.equals("Validator") -> {
                                productData.postValue(
                                    Resource.Error.Validator(
                                        response.message!!,
                                        response.data!!
                                    )
                                )
                            }
                            response.categoryError.equals("Internal") -> {
                                productData.postValue(
                                    Resource.Error.Internal(
                                        response.message!!,
                                        response.data!!
                                    )
                                )
                            }
                            else -> {
                                productData.postValue(
                                    Resource.Error.UnKnownError(
                                        response.message!!,
                                        response.data!!
                                    )
                                )

                            }
                        }

                    }
                    is DataState.Empty -> {
                        productData.postValue(Resource.Empty())
                    }
                }
            }
        })
    }

    fun loadDataAgain() {
        productRepository.getProduct(query, object : ResponseWrapper {
            override fun onResponse(response: DataState<LiveData<List<Product>>>) {
                when (response) {
                    is DataState.Success -> {
                        println("log Success for repository")
                        productData.postValue(Resource.Success(response.data!!))
                    }
                    is DataState.Error -> {
                        println("log Error for repository")
                        when {
                            response.categoryError.equals("Validator") -> {
                                productData.postValue(
                                    Resource.Error.Validator(
                                        response.message!!,
                                        response.data!!
                                    )
                                )
                            }
                            response.categoryError.equals("Internal") -> {
                                productData.postValue(
                                    Resource.Error.Internal(
                                        response.message!!,
                                        response.data!!
                                    )
                                )
                            }
                            else -> {
                                productData.postValue(
                                    Resource.Error.UnKnownError(
                                        response.message!!,
                                        response.data!!
                                    )
                                )

                            }
                        }

                    }
                    is DataState.Empty -> {
                        productData.postValue(Resource.Empty())
                    }
                }
            }
        })
    }
}