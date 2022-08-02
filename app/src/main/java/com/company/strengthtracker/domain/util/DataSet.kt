package com.company.strengthtracker.domain.util

import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DataSet(
    val coordinateArray: Array<MutableList<Float>>,
) {
    var xMax = Float.MIN_VALUE
    var xMin = Float.MAX_VALUE
    var yMax = Float.MIN_VALUE
    var yMin = Float.MAX_VALUE

    fun init() {
        coordinateArray[0].forEach { x ->
                xMax = if (x > xMax) x else xMax
                xMin = if (x < xMin) x else xMin
        }
        coordinateArray[1].forEach { y ->
            yMax = if (y > yMax) y else yMax
            yMin = if (y < yMin) y else yMin
        }
    }
}