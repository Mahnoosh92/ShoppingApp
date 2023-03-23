package com.mahnoosh.dashboard.data.di

import com.mahnoosh.dashboard.domain.repository.CategoryRepository
import com.mahnoosh.dashboard.presentation.DashboardViewModel
import com.mahnoosh.utils.IO_DISPATCHER
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val ViewModelModule = module {
    viewModel {
        provideDashboardViewModel(categoryRepository = get(), ioDispatcher = get(named(IO_DISPATCHER)))
    }
}

private fun provideDashboardViewModel(
    categoryRepository: CategoryRepository,
    ioDispatcher: CoroutineDispatcher
) = DashboardViewModel(categoryRepository = categoryRepository, ioDispatcher = ioDispatcher)