package com.company.strengthtracker.presentation.login_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.company.strengthtracker.data.entities.User
import com.company.strengthtracker.data.repository.AuthRepositoryImpl
import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {



    // Possible states that this screen should take
    enum class LoginScreenState {
        LAUNCH, STANDBY, LOADING, LOGIN_SUCCESS, LOGIN_FAILURE
    }

    // Referenced in LoginScreen to determine which UI should be presented to user
    var loginScreenState = mutableStateOf(LoginScreenState.LAUNCH)

    // Equal to null if not currently logged in
    var currentUser: MutableState<FirebaseUser?> = mutableStateOf(null)

    init{
        isUserLoggedIn()
    }

    // Invoked on initialization to determine if user is already logged-in
    fun isUserLoggedIn() {
        println("Is used logged in invoked")
        loginScreenState.value = LoginScreenState.LOADING

        viewModelScope.launch {
            currentUser.value = authRepositoryImpl.getCurrentUser()
            loginScreenState.value =
                if (currentUser.value == null)
                    LoginScreenState.STANDBY else LoginScreenState.LOGIN_SUCCESS
        }
    }

    // Invoked by user to attempt a login
    fun loginUser(
        email: String,
        password: String
    ) {
        loginScreenState.value = LoginScreenState.LOADING

        viewModelScope.launch {
            val response = authRepositoryImpl.login(
                email = email,
                password = password
            )

            // Update current user
            currentUser.value = response.data
            loginScreenState.value = when (response) {
                is Resource.Success -> LoginScreenState.LOGIN_SUCCESS
                is Resource.Error -> LoginScreenState.LOGIN_FAILURE
            }

        }
    }


}