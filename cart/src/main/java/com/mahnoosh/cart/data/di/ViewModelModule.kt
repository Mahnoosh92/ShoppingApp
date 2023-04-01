package com.mahnoosh.cart.data.di

import com.mahnoosh.cart.domain.repository.CartRepository
import com.mahnoosh.cart.presentation.CartViewModel
import com.mahnoosh.utils.MAIN_DISPATCHER
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val CartViewModelModule = module {
    viewModel {
        provideCartViewModel(
            cartRepository = get(), mainDispatcher = get(named(MAIN_DISPATCHER))
        )
    }
}

private fun provideCartViewModel(
    cartRepository: CartRepository, mainDispatcher: CoroutineDispatcher
): CartViewModel = CartViewModel(
    cartRepository = cartRepository, mainDispatcher = mainDispatcher
)