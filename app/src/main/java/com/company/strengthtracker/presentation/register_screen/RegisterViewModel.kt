package com.company.strengthtracker.presentation.register_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.strengthtracker.data.entities.User
import com.company.strengthtracker.data.repository.AuthRepositoryImpl
import com.company.strengthtracker.data.repository.UsersRepositoryImpl
import com.company.strengthtracker.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val usersRepositoryImpl: UsersRepositoryImpl
) : ViewModel() {

    // Possible states that this screen should take
    enum class RegisterScreenState {
        LAUNCH, LOADING, REGISTER_SUCCESS, REGISTER_ERROR
    }

    // Referenced in screen to determine which UI should be presented to user
    var registerScreenState = mutableStateOf(RegisterScreenState.LAUNCH)

    fun registerUser(
        email: String,
        password: String,
    ) {
        registerScreenState.value = RegisterScreenState.LOADING

        viewModelScope.launch {
            val response = authRepositoryImpl.registerUser(email, password)
            if (response is Resource.Success)
                usersRepositoryImpl.createUser(
                    User(
                        uid = response.data,
                        email = email,
                    )
                )

            registerScreenState.value = when (response) {
                is Resource.Success -> RegisterScreenState.REGISTER_SUCCESS
                is Resource.Error -> RegisterScreenState.REGISTER_ERROR
            }
        }
    }

    fun checkPass(
        password: String
    ): Boolean {
        var pattern: Pattern

        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"

        pattern = Pattern.compile(passwordPattern)
        var matcher: Matcher = pattern.matcher(password)

        return matcher.matches()
    }

    fun reset() {
        registerScreenState.value = RegisterScreenState.LAUNCH
    }
}