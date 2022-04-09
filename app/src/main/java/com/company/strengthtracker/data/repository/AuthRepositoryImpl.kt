package com.company.strengthtracker.data.repository

import com.company.strengthtracker.data.entities.User
import com.company.strengthtracker.domain.repository.AuthRepository
import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

// https://youtu.be/bekW_W3A2EY?t=250
// Class responsible for:
// Firebase Sign-in and Sign-out operations, both async
// Checking authentication
// Actual implementation of AuthRepository interface

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth // Implementation of Authentication
) : AuthRepository {

    override suspend fun getCurrentUser(): FirebaseUser? = auth.currentUser

    override suspend fun registerUser(email: String, password: String): Resource<Boolean> {
        return try {
            val response = auth.createUserWithEmailAndPassword(
                email,
                password
            ).await()
            if (response.user != null) Resource.Success(true)
            else Resource.Error("some error")

        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun login(email: String, password: String): Resource<Boolean> {
        return try {
            val response = auth.signInWithEmailAndPassword(
                email,
                password
            ).await()
            println("here response is successful ${response.user}" +
                    "${response.additionalUserInfo}")
            if (response.user != null) Resource.Success(true)
            else Resource.Error("${response}")

        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }    }
}