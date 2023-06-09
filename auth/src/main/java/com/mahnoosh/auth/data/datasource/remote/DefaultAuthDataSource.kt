package com.mahnoosh.auth.data.datasource.remote

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DefaultAuthDataSource(
    private val auth: FirebaseAuth
) : AuthDataSource {
    override suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult =
        suspendCancellableCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(task.result)
                    } else {
                        continuation.resumeWithException(
                            task.exception ?: Exception("Something went wrong!")
                        )
                    }
                    continuation.invokeOnCancellation {

                    }
                }
        }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult = suspendCancellableCoroutine { continuation ->
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(task.result)
            } else {
                continuation.resumeWithException(
                    task.exception ?: Exception("Something went wrong!")
                )
            }
        }
        continuation.invokeOnCancellation {

        }
    }
}

interface LogInListener {
    fun logInSuccess(email: String?, password: String?)
    fun logInFailure(exception: java.lang.Exception?, email: String?, password: String?)
}