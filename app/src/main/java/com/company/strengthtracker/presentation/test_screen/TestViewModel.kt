package com.company.strengthtracker.presentation.test_screen

import androidx.lifecycle.ViewModel
import com.company.strengthtracker.data.repository.AuthRepositoryImpl
import com.company.strengthtracker.data.repository.UsersRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

//test viewModel for holding input data for different graphs Im working on
@HiltViewModel
class TestViewModel
@Inject
constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val usersRepositoryImpl: UsersRepositoryImpl
) : ViewModel() {
    val listX: MutableList<Float> = mutableListOf()
    val listY: MutableList<Float> =
    val listYCurrent: MutableList<Float> =

    val listXCurrent = listX
    val yMax: Float = listY.maxOrNull() ?: -1f

    val xMax: Float = listX.maxOrNull() ?: -1f
    val yMin: Float = listY.minOrNull() ?: -1f
    val xMin: Float = listX.minOrNull() ?: -1f

    fun tempGetList(l:MutableList<Float>): MutableList<Float>{

    }
}
