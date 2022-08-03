package com.company.strengthtracker.domain.util

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class GraphDataList(
    var coordinates: MutableList<DataSet>
) {
    val totalYMax = mutableStateOf(Float.MIN_VALUE)
    val totalXMax = mutableStateOf(Float.MIN_VALUE)
    val totalYMin = mutableStateOf(Float.MAX_VALUE)
    val totalXMin = mutableStateOf(Float.MAX_VALUE)

    fun getMaxes() {
        coordinates.forEach { dataSet ->
            getMax(dataSet.coordinateArray[0], dataSet.coordinateArray[1])
        }
    }

    fun getMax(x: MutableList<Float>, y: MutableList<Float>) {
        x.forEach { xVal ->
            totalXMax.value = maxOf(xVal, totalXMax.value)
            totalXMin.value = minOf(xVal, totalXMin.value)
        }
        y.forEach { yVal ->
            totalYMax.value = maxOf(yVal, totalYMax.value)
            totalYMin.value = minOf(yVal, totalYMin.value)

        }

    }
}