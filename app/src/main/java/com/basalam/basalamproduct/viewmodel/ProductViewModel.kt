package com.basalam.basalamproduct.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.basalam.basalamproduct.util.Resource
import com.basalam.domain.entities.ProductEntity
import com.basalam.domain.usecases.GetProductUseCase
import com.basalam.domain.utils.DataState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProductViewModel @ViewModelInject constructor(
    private val productUseCase: GetProductUseCase
) : ViewModel() {
    val productData: MutableLiveData<Resource<LiveData<List<ProductEntity>>>> = MutableLiveData()

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            println("log view model ")
            productUseCase.getProductUseCase().collect { responseUseCase ->
                when (responseUseCase) {
                    is DataState.Success -> {
                        productData.postValue(
                            Resource.Success(
                                responseUseCase.data?.asLiveData(
                                    coroutineContext
                                )!!
                            )
                        )
                    }

                    is DataState.Empty -> {
                        productData.postValue(Resource.Empty())
                    }
                    is DataState.Error -> {
                        when {
                            responseUseCase.categoryError.equals("Validator") -> {
                                productData.postValue(
                                    Resource.Error.Validator(
                                        responseUseCase.message!!,
                                        responseUseCase.data?.asLiveData(coroutineContext)!!
                                    )
                                )
                            }
                            responseUseCase.categoryError.equals("Internal") -> {
                                productData.postValue(
                                    Resource.Error.Internal(
                                        responseUseCase.message!!,
                                        responseUseCase.data?.asLiveData(coroutineContext)!!
                                    )
                                )
                            }
                            else -> {
                                productData.postValue(
                                    Resource.Error.UnKnownError(
                                        responseUseCase.message!!,
                                        responseUseCase.data?.asLiveData(coroutineContext)!!
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
