package com.company.strengthtracker.domain.use_cases.data.repository

import com.company.strengthtracker.data.entities.exercise_data.exercise_definitions.Planche
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.repository.SetRepositoryImpl
import com.company.strengthtracker.domain.repository.SetRepository
import com.company.strengthtracker.domain.util.Resource
import kotlinx.coroutines.flow.flow

class FakeLogRepository : SetRepository {

    val log = mutableListOf<MutableList<AllExercises>>()

    override suspend fun setUpdater(
        exerciseBundle: MutableList<MutableList<AllExercises>>,
        date: String,
        userUid: String
    ): Resource<MutableList<MutableList<AllExercises>>> {
        return Resource.Success(log)
    }

    override suspend fun addSet(movement: AllExercises): Resource<String> {
        if(log.size == 0){
            log.add(mutableListOf(movement))
        }
        else {
            for (i in 0..(log.size - 1)) {
                if(log[i][0].name == movement.name || log[i][0].equals(null)){
                    log[i].add(movement)
                }
            }
        }
        return Resource.Success("add-success")
    }


}