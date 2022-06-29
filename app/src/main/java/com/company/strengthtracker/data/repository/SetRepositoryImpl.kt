package com.company.strengthtracker.data.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Dynamics
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics
import com.company.strengthtracker.di.AppModule
import com.company.strengthtracker.domain.repository.AuthRepository
import com.company.strengthtracker.domain.repository.SetRepository
import com.company.strengthtracker.domain.util.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.nio.file.Path
import java.time.LocalDate
import javax.inject.Inject

//
class SetRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : SetRepository {

    override suspend fun getAllExerciseSubcollection(
        date: String,
        userUid: String
    ): Resource<QuerySnapshot> {
        return try {
            //reference to collection of exercises logged by the user on a specified date
            val allExercisesRef = db.collection(userUid)
                .document(date)
                .collection(date).get().await()
            if (!allExercisesRef.isEmpty)
                Resource.Success(allExercisesRef)
            else if (allExercisesRef.isEmpty) {
                Resource.Error("empty-day")
            } else
                Resource.Error("Error retrieving collection for this date")
        } catch(e:Exception){
           return Resource.Error("exception thrown ")
        }
    }

    override suspend fun getSetSubcollection(
        date: String,
        userUid: String,
        exName: String
    ): Resource<QuerySnapshot> {
        return try {
            val setsRef = db.collection(userUid).document(date).collection(date).document(exName)
                .collection(exName).get().await()

            if (setsRef.isEmpty)
                Resource.Error("Error retrieving sets for this exercise")
            else
                Resource.Success(setsRef)
        } catch (e: Exception) {
           return Resource.Error("Exception thrown")
        }
    }

    override suspend fun createLogPath(
        fields: HashMap<String, String>,
        userUid: String,
        date: String,
        name: String,
        exType: String
    ): Resource<String> {
        return try {
            db.collection(userUid).document(date).collection(date).document(name).set(fields)
                .await()
            Resource.Success("path created")
        } catch (e: Exception) {
           return Resource.Error("Error creating correct path for logging")
        }
    }

    override suspend fun addSet(
        movement:AllExercises,
        date: String,
        userUid: String
    ): Resource<String> {
        return try{
            val newDoc = db.collection(userUid).document(date).collection(date).document(movement.name).collection(movement.name).add(movement).await()
            if(newDoc != null){
                Log.d(TAG, "Successful add, throwing success back to use case")
                Resource.Success("Successful add")
            } else
                Resource.Error("Failed add")
        } catch(e:Exception){
           return Resource.Error("exception when adding to database")
        }
    }


}