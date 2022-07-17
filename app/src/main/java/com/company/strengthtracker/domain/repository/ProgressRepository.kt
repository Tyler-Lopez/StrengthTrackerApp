package com.company.strengthtracker.domain.repository

import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.firestore.QuerySnapshot
import java.time.LocalDate

interface ProgressRepository {
    suspend fun getHistory(startDate: Long, endDate: Long, userUid:String): Resource<QuerySnapshot>
    suspend fun checkHistory():Resource<QuerySnapshot>
    suspend fun checkPriorHistory():Resource<QuerySnapshot>
}