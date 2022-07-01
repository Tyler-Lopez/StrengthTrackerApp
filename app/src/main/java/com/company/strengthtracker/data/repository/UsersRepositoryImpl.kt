package com.company.strengthtracker.data.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.company.strengthtracker.data.entities.User
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Dynamics
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics
import com.company.strengthtracker.domain.repository.UsersRepository
import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersCollection: CollectionReference,
    private val db: FirebaseFirestore = Firebase.firestore
) : UsersRepository {

    override suspend fun getUserByUid(uid: String): Resource<User?> {
        return try {
            val response = usersCollection.document(uid).get().await()
            if (response != null) {
                val user = response.toObject(User::class.java)
                return Resource.Success(data = user)
            } else Resource.Error("Error in login")

        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun createUser(user: User) {
        usersCollection.document(user.uid).set(user).await()
    }

    override suspend fun updateUserUsernameByUid(uid: String, username: String) {
        // https://firebase.google.com/docs/firestore/manage-data/add-data#kotlin+ktx
        val data = hashMapOf("username" to username)
        usersCollection.document(uid).update(data as Map<String, Any>).await()
    }

    override suspend fun deleteUserByUid(uid: String) {
        usersCollection.document(uid).delete()
    }
}