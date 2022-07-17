package com.company.strengthtracker.presentation.test_screen

import androidx.lifecycle.ViewModel
import com.company.strengthtracker.data.repository.AuthRepositoryImpl
import com.company.strengthtracker.data.repository.UsersRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
//test viewModel for holding input data for different graphs Im working on
@HiltViewModel
class TestViewModel
@Inject
constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val usersRepositoryImpl: UsersRepositoryImpl
) : ViewModel() {
    val listX: List<Float> =
        listOf(1f, 3f, 5f, 7f, 9f, 11f, 13f, 15f, 17f, 19f, 21f, 23f, 25f, 27f, 29f, 31f)
    val listY: List<Float> =
        listOf(25f, 25f, 30f, 35f, 40f, 45f, 52.5f, 50f, 50f, 52.5f, 55f, 50f, 60f, 60f, 65f, 65f)
    val yMax: Float = listY.maxOrNull() ?: -1f
    val xMax: Float = listX.maxOrNull() ?: -1f
    val yMin:Float = listY.minOrNull() ?: -1f
    val xMin: Float = listX.minOrNull() ?: -1f

}
