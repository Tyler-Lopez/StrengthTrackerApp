package com.company.strengthtracker.domain.use_cases

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Dynamics
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics
import com.company.strengthtracker.data.repository.SetRepositoryImpl
import com.company.strengthtracker.data.repository.UsersRepositoryImpl
import com.company.strengthtracker.domain.repository.SetRepository
import com.company.strengthtracker.domain.util.Resource
import com.company.strengthtracker.presentation.template_day_screen.DayViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UpdateViewmodelLogUseCase @Inject constructor(
    private val setRepositoryImpl: SetRepository
) {
    suspend fun updateViewLog(
        date:String,
        userUid:String,
    ):Resource<MutableList<MutableList<AllExercises>>>{
        val bundle:MutableList<MutableList<AllExercises>> = mutableListOf()

        val response = setRepositoryImpl.getAllExerciseSubcollection(date = date, userUid = userUid)
        when(response) {
            is Resource.Success -> {
                response.data.documents.forEach { exerciseCategory ->
                    val setsRef = setRepositoryImpl.getSetSubcollection(
                        date,
                        userUid,
                        exerciseCategory.get("name").toString()
                    )
                    when(setsRef) {
                        is Resource.Success -> {
                            //setsRef.data.documents.forEach {

                                val collect = collectDataFromLog(
                                    setsRef = setsRef.data,
                                    exType = exerciseCategory.get("exType").toString(), setBundle = mutableListOf())

                                when(collect){
                                    is Resource.Success -> {
                                        bundle.add(collect.data)
                                    }
                                    is Resource.Error -> {
                                        return Resource.Error(message = collect.message)
                                    }
                                }
                            //}
                        }
                        is Resource.Error -> {
                            return Resource.Error(setsRef.message)
                        }
                    }
                }
            }
            is Resource.Error -> {
                return Resource.Error(response.message)
            }
        }
        return Resource.Success(bundle)
    }

    private suspend fun collectDataFromLog(
        setBundle:MutableList<AllExercises>,
        setsRef: QuerySnapshot,
        exType:String,
    ): Resource<MutableList<AllExercises>> {
        setsRef.documents.forEach { set ->
                //Create objects based off of fields in documents
                if (set != null) {
                    if (exType == "STATIC") {
                        setBundle.add(
                            Statics(
                                name = set.get("name") as String,
                                holdTime = set.get("holdTime") as String,
                                weight = set.get("weight") as String,
                                sir = set.get("sir") as String,
                                progression = set.get("progression") as String,
                                setNumber = set.get("setNumber") as Long,
                                iconId = -1,
                                properties = set.get("properties") as MutableMap<String, Boolean>
                            )
                        )
                    }
                    if (exType == "DYNAMIC") {
                        setBundle.add(
                            Dynamics(
                                name = set.get("name") as String,
                                weight = set.get("weight") as String,
                                reps = set.get("reps") as String,
                                rir = set.get("rir") as String,
                                setNumber = set.get("setNumber") as Long,
                                iconId = -1,
                                properties = set.get("properties") as MutableMap<String, Boolean>
                            )
                        )
                    }
                }
            else return Resource.Error("Error getting set data")
        }
        return Resource.Success(setBundle)
    }





}
//
//
//    fun getSetDataForDate() {
//        viewModelScope.launch { ->
//            val fetchData: Resource<MutableList<MutableList<AllExercises>>>
//            val exerciseBundleTemp:MutableList<MutableList<AllExercises>> = mutableListOf()
//
//            //get logged in users UID
//            val response = authRepositoryImpl.getCurrentUser()?.uid
//            if(response != null){
//                //Call set updater to refresh list of logged exercises
//                fetchData = usersRepositoryImpl.setUpdater(date = dateIn.value.toString(), userUid = response, exerciseBundle = exerciseBundleTemp)
//
//                //Success
//                if(fetchData is Resource.Success && fetchData.data.size > 0){
//                    Log.d(ContentValues.TAG, "Size of bundle in VM ${exerciseBundleMain.size}")
//                    exerciseBundleMain.addAll(fetchData.data)
//                    Log.d(ContentValues.TAG, "Size of bundle ${exerciseBundleMain.size}")
//                }
//
//                dayScreenState.value = when (fetchData) {
//                    is Resource.Success-> DayViewModel.DayScreenState.LOADED
//                    is Resource.Error -> DayViewModel.DayScreenState.LAUNCH
//                }
//            }
////            _dayScreenState.value = DayScreenState.LOADED
//        }
//
//    }
