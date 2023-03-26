package com.mahnoosh.product.data.di

import android.app.Application
import com.mahnoosh.product.domain.repository.ProductRepository
import com.mahnoosh.product.presentation.product_list.ProductListViewModel
import com.mahnoosh.utils.providers.ResourceProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel {
        provideProductListViewModel(productRepository = get(), resourceProvider = get())
    }
}

private fun provideProductListViewModel(productRepository: ProductRepository, resourceProvider: ResourceProvider): ProductListViewModel =
    ProductListViewModel(productRepository = productRepository, resourceProvider = resourceProvider)