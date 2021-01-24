package com.basalam.basalamproduct.di

import com.basalam.data.repository.ProductCacheImpl
import com.basalam.data.repository.ProductCacheStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class CacheModule {
    @Provides
    @Singleton
    fun provideCacheRepository(cacheImpl: ProductCacheImpl): ProductCacheStore {
        return cacheImpl
    }
}