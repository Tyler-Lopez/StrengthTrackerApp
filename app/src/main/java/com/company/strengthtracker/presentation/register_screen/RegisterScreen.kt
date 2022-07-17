package com.company.strengthtracker.presentation.register_screen

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.strengthtracker.R
import com.company.strengthtracker.Screen
import com.company.strengthtracker.presentation.register_screen.RegisterViewModel.RegisterScreenState.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember{ SnackbarHostState() }

    val screenState by remember { viewModel.registerScreenState }
    val context = LocalContext.current

    when (screenState) {
        LOADING -> {
            Text(text = "LOADING")
        }
        REGISTER_SUCCESS -> {
                    navController.navigate(Screen.DayScreen.route) {
                        popUpTo(Screen.DayScreen.route) {
                            inclusive = true
                        }
                    }
            }
        REGISTER_ERROR -> {
            Column {
                Text(text = "ERROR")
                Button(onClick = {
                    viewModel.reset()
                }) {
                    Text("click to reset")
                }
            }
        }
        // Present registration UI
        LAUNCH -> {

            /* COLUMNS */
            // This column fills all nested composables to the entire size of the screen and centers
            Column(
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var newUserEmail by remember { mutableStateOf("") }
                var newUserDob by remember { mutableStateOf("") }
                var newUserPassText by remember { mutableStateOf("") }
                var passConfirmation by remember { mutableStateOf("") }
                var passwordVis by remember { mutableStateOf(false) }


                    Text(text = "Strength Tracker", fontSize = MaterialTheme.typography.titleLarge.fontSize, fontStyle = FontStyle.Normal , fontFamily = FontFamily.Monospace,fontWeight = MaterialTheme.typography.titleLarge.fontWeight,)
                    Box(modifier = Modifier.fillMaxWidth(1f), contentAlignment = Alignment.Center) {
                        Icon(painter = painterResource(id = R.drawable.hristov_planche_smooth), contentDescription = "launch icon", /*modifier = Modifier.scale(scaleX = 2f, scaleY = 2f)*/)
                        //Image(painter = painterResource(id = R.drawable.hristov_planche_smooth), contentDescription = "launch icon", contentScale = ContentScale.FillWidth)
                    }

                Card(modifier = Modifier.fillMaxWidth(0.8f)) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        //email textfield
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 3.dp, 0.dp, 3.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            value = newUserEmail,
                            onValueChange = { newUserEmail = it },
                            label = { Text("Email") },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                            ),
                            shape = MaterialTheme.shapes.medium
                        )
                            //Password textfield
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 3.dp, 0.dp, 3.dp),
                            visualTransformation = if (passwordVis) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            value = newUserPassText,

                            onValueChange = {
                                newUserPassText = it
                            },
                            trailingIcon = { },
                            label = { Text("Password") },
                            colors = TextFieldDefaults.textFieldColors(

                                focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                            ),
                            shape = MaterialTheme.shapes.medium
                        )
                        //Confirm Password textfield
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 3.dp, 0.dp, 3.dp),
                            visualTransformation = if (passwordVis) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            value = passConfirmation,
                            onValueChange = {
                                passConfirmation = it
                            },
                            label = { Text("Confirm password") },
                            colors = TextFieldDefaults.textFieldColors(

                                focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                            ),
                            shape = MaterialTheme.shapes.medium
                        )
                        Button(
                            modifier = Modifier.fillMaxWidth(0.6f),
                            onClick = {
                                if (newUserPassText == passConfirmation) {
                                    if(viewModel.checkPass(newUserPassText))
                                        viewModel.registerUser(
                                            email = newUserEmail,
                                            password = newUserPassText,
                                        )
                                    else {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Not enough special characters")
                                        }
                                    }
                                } else {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Passwords do not match")
                                    }
                                }
                            }

                        ) {
                            Text(text = "Create Account")
                        }
                    }
                }
            }
            SnackbarHost(hostState = snackbarHostState)
        }
    }

}

fun Context.dynamicToast(msg : String) {
    val toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
    toast.setGravity(Gravity.TOP, 0, 0)
    toast.show()
}

