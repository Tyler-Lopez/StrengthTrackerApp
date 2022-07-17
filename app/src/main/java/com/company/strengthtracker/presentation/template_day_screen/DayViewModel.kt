package com.company.strengthtracker.presentation.template_day_screen

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.strengthtracker.data.entities.exercise_data.exercise_definitions.FrontLever
import com.company.strengthtracker.data.entities.exercise_data.exercise_definitions.Planche
import com.company.strengthtracker.data.entities.exercise_data.main_categories.*
import com.company.strengthtracker.data.repository.AuthRepositoryImpl
import com.company.strengthtracker.data.repository.SetRepositoryImpl
import com.company.strengthtracker.domain.use_cases.AddSetToLogUseCase
import com.company.strengthtracker.domain.use_cases.UpdateViewmodelLogUseCase
import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class DayViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val logRepositoryImpl: SetRepositoryImpl,
    private val updateViewModel: UpdateViewmodelLogUseCase,
    private val addSetUseCase: AddSetToLogUseCase
) : ViewModel() {
    // registerScreenState.value = RegisterScreenState.LOADING


    //db reference for queries
    val db = Firebase.firestore

    private val _exerciseTypes = mutableStateOf(TypeDictionary().typeDictionary)
    val exerciseTypes = _exerciseTypes

    //state enum for UI control
    enum class DayScreenState {
        LAUNCH, LOADING, LOADED, SELECT, EMPTY, ERROR, SELECTED
    }


    //screenstate
    private val _dayScreenState = mutableStateOf(DayScreenState.LAUNCH)

    //visible screenstate
    val dayScreenState = _dayScreenState

    //set list for selected date
    private val _exerciseBundleMain = mutableStateListOf<MutableList<AllExercises>>()

    //visible set list
    val exerciseBundleMain = _exerciseBundleMain

    private val _exList = mutableStateListOf<AllExercises>()
    val exList = _exList

    //date-default: current date
    private var _dateIn = mutableStateOf(LocalDate.now())
    private var _dateMillis = mutableStateOf(_dateIn.value.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    val dateIn = _dateIn
    val dateMillis = _dateMillis
    var date = mutableStateOf(formatter.format(dateIn.value))//for UI display

    fun updateDate(newValue: LocalDate) {
        dayScreenState.value = DayScreenState.LAUNCH
        exerciseBundleMain.clear()
        //dayScreenState.value = DayScreenState.LOADING
        dateIn.value = newValue
        dateMillis.value = dateIn.value.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        Log.d(TAG, "date value ----> " + _dateIn.value.toString())
        getSetDataForDate()
    }

    fun openSelection() {
        dayScreenState.value = DayScreenState.SELECT
        Log.d(TAG, "SCREENSTATE IS ----> ${_dayScreenState.value}")
    }

    fun closeSelection() {
        if(exerciseBundleMain.size > 0) {
            Log.d(TAG, "Bundle holds ---->" + exerciseBundleMain[0][0].name)
            dayScreenState.value = DayScreenState.LOADED

        }
        else {
            dayScreenState.value = DayScreenState.LAUNCH
            getSetDataForDate() //refresh UI after closing selection
        }
        Log.d(TAG, "SCREENSTATE IS-----> ${_dayScreenState.value}")
    }

    init {
        getSetDataForDate()
    }


    fun updateBundle(list: MutableList<AllExercises>) {
        exerciseBundleMain.add(list)
    }

    private fun getSetDataForDate() {
        runBlocking{ ->
//            .value = DayScreenState.LAUNCH
            //get logged in users UI
            exerciseBundleMain.clear()
            val response = authRepositoryImpl.getCurrentUser()?.uid
            if (response != null) {
                //Call set updater to refresh list of logged exercises
                val updateLog = updateViewModel.updateViewLog(
                    date = dateMillis.value.toString(),
                    userUid = response
                )
                //Success
                when (updateLog) {
                    is Resource.Success -> {
//                        exerciseBundleMain.clear()
                        exerciseBundleMain.addAll(updateLog.data)
                        Log.d(TAG, "new bundle size ----> " + exerciseBundleMain.size.toString())
                        dayScreenState.value = DayScreenState.LOADED
                    }
                    is Resource.Error -> {
                        if (updateLog.message == "empty-day") {
                            dayScreenState.value = DayScreenState.EMPTY
                        } else {
                            dayScreenState.value = DayScreenState.ERROR
                        }
                    }
                }
            }
        }
    }
//    fun testfunction() {
//        viewmodelscope.launch {
//            logrepositoryimpl.gethistory(localdate.of())
//        }
//
//    }

    fun addSetHelp(
        movement: AllExercises,
    ) {
        runBlocking {
            val response = authRepositoryImpl.getCurrentUser()
            if (response != null) {
                when (addSetUseCase.addSet(movement, dateMillis.value, response.uid)) {
                    is Resource.Success -> {
                        getSetDataForDate()
                    }
                    is Resource.Error -> {
                        //TODO: change this at some point to be actually good
                        dayScreenState.value = DayScreenState.ERROR
                    }
                }
            } else {

            }
}

    }
}



