package com.company.strengthtracker.data.entities.exercise_data.main_categories

open abstract class AllExercises constructor(
    name: String,
    weight: String,
    setNumber:Long,

) {
    abstract val iconId:Int
    abstract val exType:ExState
    open var name = name
    open var weight = weight
    open var setNumber = setNumber
    abstract val reps:String

    abstract val properties: MutableMap<String, Boolean>
}