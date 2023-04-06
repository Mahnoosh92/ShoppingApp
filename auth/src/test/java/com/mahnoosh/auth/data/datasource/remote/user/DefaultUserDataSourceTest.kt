package com.mahnoosh.auth.data.datasource.remote.user

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class DefaultUserDataSourceTest {

    @Mock
    private lateinit var auth: FirebaseAuth
    private lateinit var defaultUserDataSource: DefaultUserDataSource

    @Before
    fun setup() {
        defaultUserDataSource = DefaultUserDataSource(auth = auth)
    }

    @After
    fun tearDown() {
        /*NO_OP*/
    }

    @Test
    fun `test getUser`() {
        runTest {
            defaultUserDataSource.getUser().collect()
            Mockito.verify(auth).currentUser
        }
    }
}