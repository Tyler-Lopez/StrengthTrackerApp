package com.company.strengthtracker.data.entities.exercise_data.exercise_definitions

import android.graphics.drawable.Drawable
import com.company.strengthtracker.R
import com.company.strengthtracker.data.entities.exercise_data.main_categories.ExState
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics





class Planche(
    name: String = "Planche",
    //hold time per set
    holdTime: String = "",
    //holds per set
    reps: String = "",
    //weight or assist per set
    weight: String = "",
    //seconds in reserve
    sir: String = "",
    //progression
    progression: String = "",
    //IDK
    setNumber: Long = -1,
   // displayFields:List<Boolean>,
    override val exType: ExState = ExState.STATIC,
    override val iconId: Int = R.drawable.ic_action_name
//hello
) : Statics(
    name = name,
    weight = weight,
    holdTime = holdTime,
    progression = progression,
    sir = sir,
    setNumber = setNumber,
    iconId = iconId,
    reps = reps
)
