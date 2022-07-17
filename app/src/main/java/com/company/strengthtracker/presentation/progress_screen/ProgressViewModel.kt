package com.company.strengthtracker.presentation.progress_screen

import androidx.lifecycle.ViewModel
import com.company.strengthtracker.data.repository.AuthRepositoryImpl
import com.company.strengthtracker.data.repository.UsersRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel
@Inject
constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val usersRepositoryImpl: UsersRepositoryImpl
) : ViewModel() {

}
