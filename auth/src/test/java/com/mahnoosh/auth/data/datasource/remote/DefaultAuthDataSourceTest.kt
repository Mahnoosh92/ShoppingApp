package com.mahnoosh.auth.data.datasource.remote

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.tasks.*
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
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
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executor
import org.mockito.Mockito.`when` as whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
internal class DefaultAuthDataSourceTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var successTask: Task<AuthResult>

    private lateinit var failureTask: Task<AuthResult>

    @Mock
    private lateinit var auth: FirebaseAuth

    private lateinit var defaultAuthDataSource: TestAuthDataSource

    @Before
    fun setup() {
        defaultAuthDataSource = TestAuthDataSource(auth = auth)
    }

    private val testDispatcher = StandardTestDispatcher()

    @After
    fun tearDown() {
        /*NO_OP*/
    }

    @Test
    fun `test signInWithEmailAndPassword is successful`() = runTest(testDispatcher.scheduler) {
        val email = "cool@cool.com"
        val password = "123456"

        whenever(auth.signInWithEmailAndPassword(email, password)).thenReturn(successTask)

        defaultAuthDataSource.signInWithEmailAndPassword(email, password)
        advanceUntilIdle()

//        Mockito.verify(auth).signInWithEmailAndPassword(anyString(), anyString())
    }
}