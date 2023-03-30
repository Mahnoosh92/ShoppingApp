package com.mahnoosh.product.data.di

import com.mahnoosh.product.domain.repository.ProductRepository
import com.mahnoosh.product.presentation.product.list.ProductListViewModel
import com.mahnoosh.utils.MAIN_DISPATCHER
import com.mahnoosh.utils.providers.ResourceProvider
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel {
        provideProductListViewModel(
            productRepository = get(),
            resourceProvider = get(),
            mainDispatcher = get(named(MAIN_DISPATCHER))
        )
    }
}

private fun provideProductListViewModel(
    productRepository: ProductRepository,
    resourceProvider: ResourceProvider,
    mainDispatcher: CoroutineDispatcher
): ProductListViewModel =
    ProductListViewModel(
        productRepository = productRepository,
        resourceProvider = resourceProvider,
        mainDispatcher = mainDispatcher
    )