package com.company.strengthtracker.presentation.test_screen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.company.strengthtracker.data.repository.AuthRepositoryImpl
import com.company.strengthtracker.data.repository.UsersRepositoryImpl
import com.company.strengthtracker.domain.util.DataSet
import com.company.strengthtracker.domain.util.GraphData
import com.company.strengthtracker.domain.util.GraphDataList
import com.company.strengthtracker.presentation.test_screen.graph_utils.DateFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

//test viewModel for holding input data for different graphs Im working on
@HiltViewModel
class TestViewModel
@Inject
constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val usersRepositoryImpl: UsersRepositoryImpl
) : ViewModel() {
    /*
    * r_min: min of the range of measurement
    * r_max: max of the range of measurement
    * t_min: min of the range of target scaling
    * t_max: max of the range of target scaling
    * m:
    * */
    //============TEST VALUES
    val df = DateFormatter()
    val date1 = LocalDate.of(2022, 7, 22).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val date2 = LocalDate.of(2022, 7, 24).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val date3 = LocalDate.of(2022, 7, 26).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val date4 = LocalDate.of(2022, 7, 27).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val date5 = LocalDate.of(2022, 7, 29).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val date6 = LocalDate.of(2022, 8, 4).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val date7 = LocalDate.of(2022, 8, 7).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val date8 = LocalDate.of(2022, 8, 10).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val date9 = LocalDate.of(2022, 8, 13).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val date10 = LocalDate.of(2022, 8, 15).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val graphUtil = mutableStateOf(GraphDataList(mutableListOf()))

    var x1: MutableList<Float> = mutableListOf(
        df.dateFormatter(date1),
        df.dateFormatter(date2),
        df.dateFormatter(date3),
        df.dateFormatter(date4),
        df.dateFormatter(date5)
    )
    val y1: MutableList<Float> = mutableListOf(
        50f, 45f, 40f, 35f, 40f
    )
    var x2: MutableList<Float> = mutableListOf(
        df.dateFormatter(date6),
        df.dateFormatter(date7),
        df.dateFormatter(date8),
        df.dateFormatter(date9),
        df.dateFormatter(date10)
    )
    val y2: MutableList<Float> = mutableListOf(
        60f, 62.5f, 65f, 65f, 63f
    )
    var xli = normalize(x1, y1)
    var xlc = normalize(x2, y2)
    var a = DataSet(
        coordinateArray = arrayOf(x1, y1)
    )
    var b = DataSet(
        coordinateArray = arrayOf(x2, y2)
    )

    var dataList = mutableStateOf(GraphDataList(mutableListOf()))


    init {
        a.init()
        b.init()
            dataList.value = GraphDataList(
                coordinates = mutableListOf(
                    a,
                    b
                )
            )
            dataList.value.getMaxes()
        Log.d("date", "${x1.get(0)}")
    }

//=========\\TEST VALUES

    /*normalizes xList to larger of two ranges --> this probably doesnt work but want to check*/
    fun normalize(xList: MutableList<Float>, xListb: MutableList<Float>): MutableList<Float> {
        val range = Math.max(max(xList) - min(xList), max(xListb) - min(xListb))
        println(range)
        val tempXList: MutableList<Float> = mutableListOf()
        val tempXListb: MutableList<Float> = mutableListOf()
        if (range != null) {
            xList.forEachIndexed { i, it ->
                tempXList.add((it - min(xList)) / range)
            }
            xListb.forEachIndexed { i, it ->
                tempXListb.add((it - min(xListb)) / range)
            }
        }
        return tempXList
    }

    fun max(list: MutableList<Float>): Float {
        var t = Float.MIN_VALUE
        list.forEach {
            if (t < it)
                t = it
        }
        return t
    }

    fun min(list: MutableList<Float>): Float {
        var t = Float.MAX_VALUE
        list.forEach {
            if (t > it)
                t = it
        }
        return t
    }


}
