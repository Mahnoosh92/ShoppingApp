package com.mahnoosh.dashboard.data.di

import com.mahnoosh.core.domain.repository.UserRepository
import com.mahnoosh.dashboard.domain.repository.CategoryRepository
import com.mahnoosh.dashboard.domain.repository.ProductRepository
import com.mahnoosh.dashboard.presentation.DashboardViewModel
import com.mahnoosh.dashboard.presentation.cat_products.CategoryProductsViewModel
import com.mahnoosh.dashboard.presentation.detail.DetailsViewModel
import com.mahnoosh.utils.IO_DISPATCHER
import com.mahnoosh.utils.MAIN_DISPATCHER
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val ViewModelModule = module {
    viewModel {
        provideDashboardViewModel(
            categoryRepository = get(),
            userRepository = get(),
            ioDispatcher = get(named(IO_DISPATCHER)),
            mainDispatcher = get(named(MAIN_DISPATCHER))
        )
    }
    viewModel {
        provideCategoryProductsViewModel(
            categoryRepository = get(), ioDispatcher = get(named(IO_DISPATCHER))
        )
    }
    viewModel {
        provideDetailsViewModel(
            productRepository = get(), mainDispatcher = get(named(MAIN_DISPATCHER))
        )
    }
}

private fun provideDashboardViewModel(
    categoryRepository: CategoryRepository,
    userRepository: UserRepository,
    ioDispatcher: CoroutineDispatcher,
    mainDispatcher: CoroutineDispatcher
) = DashboardViewModel(
    categoryRepository = categoryRepository,
    ioDispatcher = ioDispatcher,
    userRepository = userRepository,
    mainDispatcher = mainDispatcher
)

private fun provideCategoryProductsViewModel(
    categoryRepository: CategoryRepository, ioDispatcher: CoroutineDispatcher
) = CategoryProductsViewModel(categoryRepository = categoryRepository, ioDispatcher = ioDispatcher)

private fun provideDetailsViewModel(
    productRepository: ProductRepository, mainDispatcher: CoroutineDispatcher
) = DetailsViewModel(productRepository = productRepository, mainDispatcher = mainDispatcher)