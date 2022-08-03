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
    fun getX(): MutableList<Float>{
        return coordinateArray[0]
    }

    fun init() {
        coordinateArray[0].forEach { x ->
            xMax = maxOf(x, xMax)
            xMin = minOf(x, xMin)
        }
        coordinateArray[1].forEach { y ->
            yMax = maxOf(y, yMax)
            yMin = minOf(y, yMin)
        }
    }
}