package com.company.strengthtracker.domain.use_cases

import android.content.ContentValues.TAG
import android.util.Log
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.repository.SetRepositoryImpl
import com.company.strengthtracker.domain.repository.SetRepository
import com.company.strengthtracker.domain.util.Resource
import javax.inject.Inject

class AddSetToLogUseCase @Inject constructor(
    private val setRepositoryImpl: SetRepository
){
    suspend fun addSet(
        movement: AllExercises,
        date: String,
        userUid: String
    ):Resource<String> {
        val fields = hashMapOf(
            "name" to movement.name,
            "exType" to movement.exType.toString()
        )

        Log.d(TAG, "extype ----> ${movement.exType.toString()}")

        val createPath = setRepositoryImpl.createLogPath(fields, userUid, date, exType = movement.exType.toString(), name = movement.name)

        return when(createPath) {
            is Resource.Success -> {
                when(setRepositoryImpl.addSet(movement, date, userUid)) {
                    is Resource.Success -> {
                        Resource.Success("successful add")
                    }
                    is Resource.Error -> {
                        Resource.Error("error: failed add")
                    }
                }
            }
            is Resource.Error -> {
                Resource.Error("error: failed to create path")
            }
        }
    }
}