package com.company.strengthtracker.data.entities.exercise_data.exercise_definitions

import com.company.strengthtracker.R
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Dynamics
import com.company.strengthtracker.data.entities.exercise_data.main_categories.ExState

class PullUps(
    name: String = "Pull-ups",
    weight: String = "",
    rir:String = "",
    setNumber: Long = -1,
    reps:String = "",
    override val exType: ExState = ExState.DYNAMIC,
    override val iconId: Int = R.drawable.ic_action_name
): Dynamics(name = name,
    weight = weight,
    reps = reps,
    rir = rir,
    setNumber = setNumber,
    iconId = iconId
)