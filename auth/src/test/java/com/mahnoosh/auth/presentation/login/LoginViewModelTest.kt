package com.mahnoosh.auth.presentation.login

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
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
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception
import java.util.concurrent.Executor
import org.mockito.Mockito.`when` as whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
internal class LoginViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var authRepository: AuthRepository

    private lateinit var loginViewModel: LoginViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var successTask: AuthResult


    @Before
    fun setUp() {
        loginViewModel =
            LoginViewModel(authRepository = authRepository, mainDispatcher = testDispatcher)
    }

    @After
    fun tearDown() {
        /*NO_OP*/
    }

    @Test
    fun `test login is successful`() = runTest(testDispatcher.scheduler) {
        whenever(
            authRepository.signInWithEmailAndPassword(
                email = anyString(),
                password = anyString()
            )
        ).thenReturn(ResultWrapper.build { successTask })

        loginViewModel.loginIntent.send(LoginIntent.Login(anyString(), anyString()))
        advanceUntilIdle()

        assertThat(LiveDataTestUtil.getValue(loginViewModel.state)).isInstanceOf(LoginState.LoginStatus::class.java)
    }

    @Test
    fun `test login is not successful`() = runTest(testDispatcher.scheduler) {
        val error = "Somethong went wrong!"
        whenever(
            authRepository.signInWithEmailAndPassword(
                email = anyString(),
                password = anyString()
            )
        ).thenReturn(ResultWrapper.build { throw Exception(error) })

        loginViewModel.loginIntent.send(LoginIntent.Login(anyString(), anyString()))
        advanceUntilIdle()

        assertThat(LiveDataTestUtil.getValue(loginViewModel.state)).isEqualTo(LoginState.Error(error = error))
    }

    @Test
    fun `test create account`() = runTest(testDispatcher.scheduler) {
        loginViewModel.loginIntent.send(LoginIntent.CreateAccount)
        advanceUntilIdle()

        assertThat(LiveDataTestUtil.getValue(loginViewModel.state)).isEqualTo(LoginState.NoAccount)
    }
}