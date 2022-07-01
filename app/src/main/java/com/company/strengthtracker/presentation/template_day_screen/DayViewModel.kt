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
import com.company.strengthtracker.data.repository.LogRepositoryImpl
import com.company.strengthtracker.domain.use_cases.AddSetToLogUseCase
import com.company.strengthtracker.domain.use_cases.UpdateViewmodelLogUseCase
import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class DayViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val setRepositoryImpl: LogRepositoryImpl,
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
        LAUNCH, LOADING, LOADED, SELECT, EMPTY, ERROR
    }


    //screenstate
    private val _dayScreenState = mutableStateOf(DayScreenState.LAUNCH)

    //visible screenstate
    val dayScreenState = _dayScreenState

    //set list for selected date
    private val _exerciseBundleMain = mutableStateListOf<MutableList<AllExercises>>()

    //visible set list
    val exerciseBundleMain = _exerciseBundleMain

    val bundleTest: SnapshotStateList<MutableList<AllExercises>> = mutableStateListOf(
        mutableListOf(Planche(holdTime = "5", weight = "5")),
        mutableListOf(FrontLever(holdTime = "3", weight = "3"))
    )


    private val _exList = mutableStateListOf<AllExercises>()
    val exList = _exList

    //date-default: current date
    private var _dateIn = mutableStateOf(LocalDate.now())
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    val dateIn = _dateIn
    var date = mutableStateOf(formatter.format(dateIn.value))

    fun updateDate(newValue: LocalDate) {
        //dayScreenState.value = DayScreenState.LOADING
        _dateIn.value = newValue
        getSetDataForDate()
    }

    fun openSelection() {
        _dayScreenState.value = DayScreenState.SELECT
    }

    fun closeSelection() {
        _dayScreenState.value = DayScreenState.LOADED
    }

    init {
        getSetDataForDate()
    }


    private fun getSetDataForDate() {
        viewModelScope.launch { ->
            //get logged in users UI
            val response = authRepositoryImpl.getCurrentUser()?.uid
            if (response != null) {
                //Call set updater to refresh list of logged exercises
                //val updateLog = updateViewModel.updateViewLogUseCase(date = dateIn.value.toString(), userUid = response)
                val updateLog = updateViewModel.updateViewLog(
                    date = dateIn.value,
                    userUid = response
                )
                //Success
                when (updateLog) {
                    is Resource.Success -> {
                        exerciseBundleMain.clear()
                        exerciseBundleMain.addAll(updateLog.data)
                        Log.d(TAG, exerciseBundleMain.size.toString())
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

    fun addSetHelp(movement: AllExercises,
    ) {
        viewModelScope.launch{
            val response = authRepositoryImpl.getCurrentUser()
            if(response != null) {
                when(addSetUseCase.addSet(movement, dateIn.value.toString(), response.uid)) {
                    is Resource.Success -> {
                        getSetDataForDate()
                    }
                    is Resource.Error -> {
                        //TODO: change this at some point to be actually good
                        dayScreenState.value = DayScreenState.ERROR
                    }
                }
            }
            else {

            }

        }
    }
}



