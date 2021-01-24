package com.basalam.basalamproduct.di

import com.basalam.data.repository.*
import com.basalam.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideProductRepository(repositoryImpl: ProductRepositoryImpl): ProductRepository {
        return repositoryImpl
    }
}