package com.company.strengthtracker.data.entities.exercise_data.main_categories

import com.company.strengthtracker.data.entities.exercise_data.main_categories.ExState.STATIC

open class Statics(
    name: String = "",

    holdTime: String = "",
    weight: String = "",
    sir: String = "",
    progression: String = "",
    setNumber: Long = -1,
    override var reps: String = "",
    override val exType: ExState = STATIC,
    override val iconId: Int = -1,

    //map for props
    override val properties: MutableMap<String, Boolean> = mutableMapOf(
        "Hold time" to false,
        "Weight" to false,
        "Seconds in reserve" to false,
        "Progression" to false,
        "Reps" to false
    )
) : AllExercises(name = name, weight = weight, setNumber = setNumber) {
    override var name = name
    open var holdTime = holdTime
    override var weight = weight
    open var sir = sir
    open var progression = progression
    override var setNumber = setNumber

}