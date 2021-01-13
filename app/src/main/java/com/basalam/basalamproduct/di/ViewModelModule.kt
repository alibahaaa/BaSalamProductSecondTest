package com.basalam.basalamproduct.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.basalam.basalamproduct.viewmodel.ProductViewModel
import com.basalam.basalamproduct.viewmodel.ViewModelFactory
import com.basalam.basalamproduct.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    protected abstract fun productViewModel(productViewModel: ProductViewModel): ViewModel
}