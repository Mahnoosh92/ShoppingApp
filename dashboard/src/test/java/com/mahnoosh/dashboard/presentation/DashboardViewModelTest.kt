package com.mahnoosh.dashboard.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.domain.repository.UserRepository
import com.mahnoosh.dashboard.data.models.local.Category
import com.mahnoosh.dashboard.domain.repository.CategoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

const val ERROR = "Something went wrong!"

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
internal class DashboardViewModelTest {

    private lateinit var viewModel: DashboardViewModel

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @Mock
    private lateinit var userRepository: UserRepository


    private val testDispatcher = StandardTestDispatcher()

    private val categories =
        listOf(Category(id = 1, name = null, image = null, creationAt = null, updatedAt = null))

    @Before
    fun setup() {
        viewModel = DashboardViewModel(
            categoryRepository = categoryRepository,
            userRepository = userRepository,
            ioDispatcher = testDispatcher,
            mainDispatcher = testDispatcher
        )
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `test getCategories is successful`() = runTest(testDispatcher.scheduler) {
        // Given
        whenever(categoryRepository.getCategories(limit = anyInt())).thenReturn(flowOf(ResultWrapper.build { categories }))
        // When
        viewModel.dashboardIntent.send(DashboardIntent.GetCategories)
        advanceUntilIdle()
        // Then
        viewModel.state.test {
            assertThat(DashboardState.Categories(categories = categories)).isEqualTo(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test getCategories is not successful`() = runTest(testDispatcher.scheduler) {
        whenever(categoryRepository.getCategories(limit = anyInt())).thenReturn(flowOf(ResultWrapper.build {
            throw java.lang.Exception(
                ERROR
            )
        }))

        viewModel.dashboardIntent.send(DashboardIntent.GetCategories)

        viewModel.state.test {
            assertThat(DashboardState.Error(error = ERROR)).isEqualTo(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}