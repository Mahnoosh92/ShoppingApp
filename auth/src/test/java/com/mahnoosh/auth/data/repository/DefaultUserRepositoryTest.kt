package com.mahnoosh.auth.data.repository

import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import com.mahnoosh.auth.data.datasource.remote.user.UserDataSource
import com.mahnoosh.core.base.ResultWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
internal class DefaultUserRepositoryTest {

    @Mock
    private lateinit var userDataSource: UserDataSource

    @Mock
    private lateinit var firebaseUser: FirebaseUser

    private lateinit var defaultUserRepository: DefaultUserRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        defaultUserRepository =
            DefaultUserRepository(userDataSource = userDataSource, ioDispatcher = testDispatcher)
    }

    @After
    fun tearDown() {
        /*NO_OP*/
    }

    @Test
    fun `test getUser is successful`() = runTest(testDispatcher.scheduler) {
        whenever(userDataSource.getUser()).thenReturn(flowOf(firebaseUser))

        defaultUserRepository.getUser().collect { result ->
            when (result) {
                is ResultWrapper.Value -> {
                    assertThat(result.value).isEqualTo(firebaseUser)
                }
                is ResultWrapper.Error -> {}
            }
        }
    }
}