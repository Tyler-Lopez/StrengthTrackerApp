package com.company.strengthtracker.domain.repository

import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.firestore.QuerySnapshot
import java.time.LocalDate

interface LogRepository {
    //    suspend fun setUpdater(
//        exerciseBundle:MutableList<MutableList<AllExercises>>,
//        date: String,
//        userUid: String
//    ):Resource<MutableList<MutableList<AllExercises>>>
    suspend fun getHistory(dateStart:LocalDate, dateEnd:LocalDate, userUid:String):Resource<QuerySnapshot>
    suspend fun createLogPath(
        fields: HashMap<String, String>,
        dateIndex: HashMap<String, LocalDate>,
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