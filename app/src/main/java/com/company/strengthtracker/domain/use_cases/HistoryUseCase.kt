package com.company.strengthtracker.domain.use_cases

import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.domain.util.Resource

class HistoryUseCase {

     /*
    suspend fun updateViewLog(
        date:String,
        userUid:String,
    ): Resource<MutableList<MutableList<AllExercises>>> {
        val bundle:MutableList<MutableList<AllExercises>> = mutableListOf()

        val response = setRepositoryImpl.getAllExerciseSubcollection(date = date, userUid = userUid)
        when(response) {
            is Resource.Success -> {
                response.data.documents.forEach { exerciseCategory ->
                    val setsRef = setRepositoryImpl.getSetSubcollection(
                        date,
                        userUid,
                        exerciseCategory.get("name").toString()
                    )
                    when(setsRef) {
                        is Resource.Success -> {
                            //setsRef.data.documents.forEach {

                            val collect = collectDataFromLog(
                                setsRef = setsRef.data,
                                exType = exerciseCategory.get("exType").toString(), setBundle = mutableListOf())

                            when(collect){
                                is Resource.Success -> {
                                    bundle.add(collect.data)
                                }
                                is Resource.Error -> {
                                    return Resource.Error(message = collect.message)
                                }
                            }
                            //}
                        }
                        is Resource.Error -> {
                            return Resource.Error(setsRef.message)
                        }
                    }
                }
            }
            is Resource.Error -> {
                return Resource.Error(response.message)
            }
        }
        return Resource.Success(bundle)

    }
    */
}