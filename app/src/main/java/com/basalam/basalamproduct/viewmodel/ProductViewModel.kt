package com.basalam.basalamproduct.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.basalam.basalamproduct.model.Product
import com.basalam.basalamproduct.repository.ProductRepository
import com.basalam.basalamproduct.util.DataState
import com.basalam.basalamproduct.util.Resource
import com.basalam.basalamproduct.util.ResponseWrapper
import javax.inject.Inject

class ProductViewModel @ViewModelInject constructor(
    private val productRepository: ProductRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val productData: MutableLiveData<Resource<LiveData<List<Product>>>> = MutableLiveData()

    init {
        getData()
    }

    fun getData() {
        productRepository.getProduct(object : ResponseWrapper {
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
        }, 2)
    }
}