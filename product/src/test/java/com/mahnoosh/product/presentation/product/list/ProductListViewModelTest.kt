@file:OptIn(ExperimentalCoroutinesApi::class)

package com.mahnoosh.product.presentation.product.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.local.Product
import com.mahnoosh.dashboard.data.models.local.Category
import com.mahnoosh.product.domain.repository.ProductRepository
import com.mahnoosh.product.utils.LiveDataTestUtil
import com.mahnoosh.utils.providers.ResourceProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

const val ERROR = "Something went wrong"

@RunWith(MockitoJUnitRunner::class)
internal class ProductListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ProductListViewModel

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var resourceProvider: ResourceProvider

    private val testDispatcher = StandardTestDispatcher()

    private val products = listOf(
        Product(
            Category(
                id = 1,
                name = null,
                image = null,
                creationAt = null,
                updatedAt = null
            ),
            creationAt = null,
            description = null,
            id = 2,
            images = null,
            price = null,
            title = null,
            updatedAt = null
        )
    )

    @Before
    fun setup() {
        viewModel = ProductListViewModel(
            productRepository = productRepository,
            resourceProvider = resourceProvider,
            mainDispatcher = testDispatcher
        )
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `test getProducts is successful`() = runTest(testDispatcher.scheduler) {
        // Given
        whenever(productRepository.getProducts(limit = anyInt(), offset = anyInt())).thenReturn(
            flowOf(ResultWrapper.build {
                products
            })
        )

        // When
        viewModel.productListIntent.send(ProductListIntent.Products)
        advanceUntilIdle()

        // Then
        assertThat(LiveDataTestUtil.getValue(viewModel.state)).isEqualTo(
            ProductListState.Products(
                products
            )
        )
    }

    @Test
    fun `test getProducts is not successful`() = runTest(testDispatcher.scheduler) {
        // Given
        whenever(productRepository.getProducts(limit = anyInt(), offset = anyInt())).thenReturn(
            flowOf(
                ResultWrapper.build { throw java.lang.Exception(ERROR) }
            )
        )
        // When
        viewModel.productListIntent.send(ProductListIntent.Products)
        advanceUntilIdle()
        // Then
        assertThat(LiveDataTestUtil.getValue(viewModel.state)).isEqualTo(
            ProductListState.Error(error = ERROR)
        )
    }
}