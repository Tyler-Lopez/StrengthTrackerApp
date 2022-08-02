package com.company.strengthtracker.domain.util

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class GraphDataList(
    var coordinates: MutableList<DataSet>
) {
    var totalYMax = mutableStateOf(Float.MIN_VALUE)
    var totalXMax = mutableStateOf(Float.MIN_VALUE)
    var totalYMin = mutableStateOf(Float.MAX_VALUE)
    var totalXMin = mutableStateOf(Float.MAX_VALUE)

    fun getMaxes() {
        coordinates.forEach { dataSet ->
            getMax(dataSet.coordinateArray[0], dataSet.coordinateArray[1])
        }
    }

    //Is this faster who knows?????
    fun getMax(x: MutableList<Float>, y: MutableList<Float>) {
        x.forEach { xVal ->
            totalXMax.value = if (xVal > totalXMax.value) xVal else totalXMax.value
            totalXMin.value = if (xVal < totalXMin.value) xVal else totalXMin.value
        }
        y.forEach { yVal ->
            totalYMax.value = if (yVal > totalYMax.value) yVal else totalYMax.value
            totalYMin.value = if (yVal < totalYMin.value) yVal else totalYMin.value

        }

    }
}