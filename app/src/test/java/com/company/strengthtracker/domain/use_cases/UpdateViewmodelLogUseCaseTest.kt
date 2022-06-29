package com.company.strengthtracker.domain.use_cases

import com.company.strengthtracker.data.entities.exercise_data.exercise_definitions.FrontLever
import com.company.strengthtracker.data.entities.exercise_data.exercise_definitions.Planche
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.repository.SetRepositoryImpl
import com.company.strengthtracker.domain.use_cases.data.repository.FakeLogRepository
import com.company.strengthtracker.domain.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class UpdateViewmodelLogUseCaseTest {

    private lateinit var updateViewModelLog: UpdateViewmodelLogUseCase
    private lateinit var fakeRepository: FakeLogRepository
    private val date = LocalDate.now().toString()
    private var testLog: MutableList<MutableList<AllExercises>> = mutableListOf()
    private lateinit var fakeUid: String

    @Before
    fun setUp() {
        fakeRepository = FakeLogRepository()
        updateViewModelLog = UpdateViewmodelLogUseCase(fakeRepository)
        date
        fakeUid = "1HdbAQP5wDhYNPvlnZ5KIlHLIqg1"
        testLog = mutableListOf<MutableList<AllExercises>>(
            mutableListOf(Planche(), Planche(), Planche(), Planche()),
            mutableListOf(FrontLever(), FrontLever(), FrontLever(), FrontLever())
        )
    }


    @Test
    fun testRetrieveNonEmptyLog() {
        runBlocking {
            testLog.forEach { it ->
                it.forEach {
                    fakeRepository.addSet(it)
                }
            }
            val updatedLog = updateViewModelLog.updateViewLogUseCase(date, fakeUid)

            assertThat(updatedLog == Resource.Success(testLog))
        }

    }

    @Test
    fun testRetriveEmptyLog() {
        runBlocking {

        }
    }

}