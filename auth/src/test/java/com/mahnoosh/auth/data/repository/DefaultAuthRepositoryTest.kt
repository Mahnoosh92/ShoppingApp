package com.mahnoosh.auth.data.repository

import com.mahnoosh.auth.data.datasource.remote.AuthDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
internal class DefaultAuthRepositoryTest {

    @Mock
    private lateinit var dataSource: AuthDataSource

    private lateinit var defaultAuthRepository: DefaultAuthRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        defaultAuthRepository =
            DefaultAuthRepository(dataSource = dataSource, ioDispatcher = testDispatcher)
    }

    @After
    fun tearDown() {
        /*NO_OP*/
    }

    @Test
    fun `test signInWithEmailAndPassword`() {
        runTest(testDispatcher.scheduler) {
            defaultAuthRepository.signInWithEmailAndPassword(
                email = anyString(),
                password = anyString()
            )

            Mockito.verify(dataSource)
                .signInWithEmailAndPassword(email = anyString(), password = anyString())
        }
    }
}