package com.company.strengthtracker.presentation.login_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.company.strengthtracker.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.strengthtracker.Screen
import com.company.strengthtracker.presentation.login_screen.LoginViewModel.LoginScreenState.*

/*

LOGIN SCREEN
Has an identifier text field, password text field, register button, and (todo)? forgot password

LOGIC:
The first thing that occurs on load is a check to see if user is already logged in.
If so, navigate them to home screen.

 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val loginScreenState by remember { viewModel.loginScreenState }

    when (loginScreenState) {
        // Display loading message
        LOADING -> {
            Text("is loading")
        }
        // User is already logged-in initially OR logged-in successfully
        LOGIN_SUCCESS -> {
            LaunchedEffect(Unit) {
                if (navController.currentBackStackEntry?.destination?.route == Screen.LoginScreen.route)
                    navController.navigate(Screen.DayScreen.route) {
                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                else navController.navigate(Screen.DayScreen.route)
            }
        }
        // Invalid login attempt
        LOGIN_FAILURE -> {
            Text("Invalid login attempt")
            navController.navigate(Screen.LoginScreen.route)
        }
        // Present Login UI to user
        STANDBY -> {
            /* COLUMNS (Also can use rows) */
            // This column fills all nested composables to the entire size of the screen and centers

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // This column limits nested composables to 80% of the screen width
                Column(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var userIdText by remember { mutableStateOf("") }
                    var userPassText by remember { mutableStateOf("") }

                    Text(text = "Strength Tracker", fontSize = MaterialTheme.typography.titleLarge.fontSize, fontStyle = FontStyle.Normal , fontFamily = FontFamily.Monospace,fontWeight = MaterialTheme.typography.titleLarge.fontWeight,)
                    Box(modifier = Modifier.fillMaxWidth(1f), contentAlignment = Alignment.Center) {
                        Icon(painter = painterResource(id = R.drawable.hristov_planche_smooth), contentDescription = "launch icon", /*modifier = Modifier.scale(scaleX = 2f, scaleY = 2f)*/)
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 0.dp, 0.dp, 4.dp),
                                value = userIdText,
                                shape = MaterialTheme.shapes.medium,
                                onValueChange = { userIdText = it },
                                label = { Text("Email or Username") },

                                colors = TextFieldDefaults.textFieldColors(

                                    focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,

                                ),
                            )
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                value = userPassText,
                                onValueChange = { userPassText = it },
                                label = { Text("Password") },
                                shape = MaterialTheme.shapes.medium,
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
//                                    unfocusedIndicatorColor = Color.Transparent,
                                )
                            )
                            /* BUTTONS */
                            // Login
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .padding(top = 8.dp),
                                onClick = {
                                    viewModel.loginUser(userIdText, userPassText)
                                }
                            ) {
                                Text(
                                    text = "Login"
                                )
                            }
                            // Register
                            Button(
                                modifier = Modifier.fillMaxWidth(0.6f),
                                onClick = {
                                    // TODO
                                    // Take controller parameter and navigate to a register screen
                                    navController.navigate("register_screen")
                                }
                            ) {
                                Text(
                                    text = "Create an Account"
                                )
                            }

                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Forgot Password
                        Text(
                            text = "Forgot Your Password?",
                            modifier = Modifier.clickable {
                                // TODO
                                // Navigate
                            }
                        )
                    }
                }
            }
        }
        LAUNCH -> {
            viewModel.isUserLoggedIn()
        }
    }
}