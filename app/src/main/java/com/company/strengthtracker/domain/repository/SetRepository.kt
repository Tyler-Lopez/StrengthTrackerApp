package com.company.strengthtracker.domain.repository

import android.content.ContentValues
import android.util.Log
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics
import com.company.strengthtracker.data.repository.SetRepositoryImpl
import com.company.strengthtracker.domain.util.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import java.lang.Exception
import java.time.LocalDate

interface SetRepository {
    //    suspend fun setUpdater(
//        exerciseBundle:MutableList<MutableList<AllExercises>>,
//        date: String,
//        userUid: String
//    ):Resource<MutableList<MutableList<AllExercises>>>
    suspend fun createLogPath(
        fields: HashMap<String, String>,
        userUid: String,
        date: String,
        name: String,
        exType: String
    ): Resource<String>

    suspend fun addSet(
        movement: AllExercises,
        date: String,
        userUid: String
    ): Resource<String>

    suspend fun getAllExerciseSubcollection(
        date: String,
        userUid: String
    ): Resource<QuerySnapshot>


    suspend fun getSetSubcollection(
        date: String,
        userUid: String,
        exName: String
    ): Resource<QuerySnapshot>
}