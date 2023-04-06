package com.mahnoosh.auth.presentation.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.AuthResult
import com.mahnoosh.auth.domain.repository.AuthRepository
import com.mahnoosh.auth.utils.LiveDataTestUtil
import com.mahnoosh.core.base.ResultWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
internal class RegisterViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var authRepository: AuthRepository

    @Mock
    private lateinit var authResult: AuthResult

    private lateinit var registerViewModel: RegisterViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        registerViewModel =
            RegisterViewModel(authRepository = authRepository, mainDispatcher = testDispatcher)
    }

    @After
    fun tearDown() {
        /*NO_OP*/
    }

    @Test
    fun `test register is successful`() = runTest(testDispatcher.scheduler) {
        whenever(
            authRepository.createUserWithEmailAndPassword(
                anyString(),
                anyString()
            )
        ).thenReturn(
            ResultWrapper.build { authResult })
        registerViewModel.registerIntent.send(RegisterIntent.RegisterUser(anyString(), anyString()))
        advanceUntilIdle()

        assertThat(LiveDataTestUtil.getValue(registerViewModel.state)).isEqualTo(
            RegisterState.RegisterStatus(
                status = true
            )
        )
    }

    @Test
    fun `test register is not successful`() = runTest(testDispatcher.scheduler) {
        val error = "error"
        whenever(
            authRepository.createUserWithEmailAndPassword(
                anyString(),
                anyString()
            )
        ).thenReturn(
            ResultWrapper.build { throw Exception(error) })
        registerViewModel.registerIntent.send(RegisterIntent.RegisterUser(anyString(), anyString()))
        advanceUntilIdle()

        assertThat(LiveDataTestUtil.getValue(registerViewModel.state)).isEqualTo(
            RegisterState.Error(
                error = error
            )
        )
    }
}