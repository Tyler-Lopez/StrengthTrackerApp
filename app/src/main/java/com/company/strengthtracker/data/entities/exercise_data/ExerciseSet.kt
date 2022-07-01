package com.company.strengthtracker.data.entities.exercise_data

import com.company.strengthtracker.data.entities.exercise_data.exercise_definitions.Planche
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics

data class ExerciseSet(
    var setList: MutableList<Statics> = mutableListOf()
    //var setNumber: Int = setList.size
)