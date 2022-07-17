package com.company.strengthtracker.data.repository

import com.company.strengthtracker.domain.repository.ProgressRepository
import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject

class ProgressRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
) : ProgressRepository {

    override suspend fun getHistory(
        dateStart: Long,
        dateEnd: Long,
        userUid: String
    ): Resource<QuerySnapshot> = try {
        val query = db.collection(userUid).whereGreaterThan("date", dateStart).whereLessThan("date", dateEnd).get().await()
        if (!query.isEmpty) {
            Resource.Success(query)
        } else (Resource.Error("range fetch was empty"))
    } catch (e: Exception) {
        Resource.Error("Exception while fetching range")
    }

    override suspend fun checkHistory(): Resource<QuerySnapshot> {
        TODO("Not impl")
    }

    override suspend fun checkPriorHistory(): Resource<QuerySnapshot> {
        TODO("not impl")
    }
}