package com.company.strengthtracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi

import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.company.strengthtracker.presentation.forgot_password_screen.ForgotPasswordScreen
import com.company.strengthtracker.presentation.login_screen.LoginScreen
import com.company.strengthtracker.presentation.test_screen.TestScreen
import com.company.strengthtracker.presentation.welcome_screen.WelcomeScreen
import com.company.strengthtracker.presentation.register_screen.RegisterScreen
import com.company.strengthtracker.presentation.template_day_screen.DayScreen

import com.company.strengthtracker.ui.theme.StrengthTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StrengthTrackerTheme {
                // NavController contains methods to change navigation
                // So if we ever want to navigate within a screen, it must be passed as a parameter
                val navController = rememberNavController()
                // NavHost defines all possible screens
                NavHost(
                    navController = navController,
                    startDestination = Screen.LoginScreen.route
                ) {
                    // Each composable() represents a Screen; pass in composable which represent screen
                    composable(route = Screen.LoginScreen.route) {
                        LoginScreen(navController = navController)
                    }
                    composable(route = Screen.RegisterScreen.route) {
                        RegisterScreen(navController = navController)
                    }
                    composable(route = Screen.ForgotPasswordScreen.route) {
                        ForgotPasswordScreen(navController = navController)
                    }
                    composable(route = Screen.WelcomeScreen.route) {
                        WelcomeScreen(navController = navController)
                    }
                    composable(route = Screen.DayScreen.route) {
                        DayScreen(navController = navController)
                    }
                    composable(route = Screen.TestScreen.route) {
                        TestScreen(navController = navController)
                    }
                }

            }
        }
    }
}
