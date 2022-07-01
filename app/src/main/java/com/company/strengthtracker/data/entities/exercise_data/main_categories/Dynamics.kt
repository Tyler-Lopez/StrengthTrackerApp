package com.company.strengthtracker.data.entities.exercise_data.main_categories
import com.company.strengthtracker.data.entities.exercise_data.main_categories.ExState.DYNAMIC
import com.company.strengthtracker.data.entities.exercise_data.main_categories.ExState.STATIC
open class Dynamics(
    name:String = "",
    weight:String = "",
    reps:String = "",
    rir: String = "",
    setNumber:Long = -1,
    override val exType:ExState = DYNAMIC,
    override val iconId:Int = -1,

    override val properties: MutableMap<String, Boolean> = mutableMapOf(

    )
):AllExercises(name=name, weight = weight, setNumber = setNumber) {

    override var name = name
    override var weight = weight
    override var reps = reps
    open var rir = rir
    override var setNumber = setNumber
}