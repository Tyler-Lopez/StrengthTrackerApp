package com.company.strengthtracker.domain.use_cases

import android.content.ContentValues.TAG
import android.util.Log
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.domain.repository.SetRepository
import com.company.strengthtracker.domain.util.Resource
import javax.inject.Inject

class AddSetToLogUseCase @Inject constructor(
    private val setRepositoryImpl: SetRepository
){
    suspend fun addSet(
        movement: AllExercises,
        date: Long,
        userUid: String,
        dateString: String = date.toString()
    ):Resource<String> {
        val dateDbIndex = hashMapOf("date" to date)
        val fields = hashMapOf(
            "name" to movement.name,
            "exType" to movement.exType.toString()
        )

        Log.d(TAG, "extype ----> ${movement.exType.toString()}")

        return when(setRepositoryImpl.createLogPath(
            fields,
            dateIndex = dateDbIndex,
            userUid,
            dateString,
            exType = movement.exType.toString(),
            name = movement.name, )) {
                is Resource.Success -> {
                    when(setRepositoryImpl.addSet(movement, dateString, userUid)) {

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