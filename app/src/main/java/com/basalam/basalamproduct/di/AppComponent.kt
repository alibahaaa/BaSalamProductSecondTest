package com.basalam.basalamproduct.di

import android.content.Context
import com.basalam.basalamproduct.adapters.ProductAdapter
import com.basalam.basalamproduct.adapters.ShimmerAdapter
import com.basalam.basalamproduct.repository.ProductRepository
import com.basalam.basalamproduct.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DataBaseModule::class, ViewModelModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context, @BindsInstance size: Int): AppComponent
    }

    fun inject(activity: MainActivity)
    fun getRepository(): ProductRepository
    fun getAdapter(): ProductAdapter
    fun getShimmerAdapter(): ShimmerAdapter
}