package com.company.strengthtracker.presentation.template_day_screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericAddPopUp(
    movement: AllExercises,
    viewModel: DayViewModel = hiltViewModel(),
    content: @Composable() () -> Unit
) {
    //controller boolean for state of pop up
    var openDialog by remember { mutableStateOf(true) }


    //display dialog

    if (openDialog) {
        Dialog(
            //dismissing open dialog
            onDismissRequest = { openDialog = false },

            //idk
            properties = DialogProperties(),

            ) {
            //Card container
            Card(
                modifier = Modifier
                    .size(300.dp, 340.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(10.dp)
//
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column() {

                        for (property in movement.properties) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextField(
                                    modifier = Modifier.padding(
                                        start = 8.dp,
                                        end = 8.dp,
                                        top = 0.dp,
                                        bottom = 0.dp
                                    ),
                                    value = property.key, onValueChange = {}, readOnly = true,
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = MaterialTheme.colorScheme.onSurface,

                                        focusedIndicatorColor = Color.Transparent,

                                        unfocusedIndicatorColor = Color.Transparent,
                                        disabledIndicatorColor = Color.Transparent,
                                        disabledLabelColor = Color.Transparent,
                                    ),
                                    trailingIcon = {
                                        var checked by remember { mutableStateOf(false) }
                                        Checkbox(
                                            checked = checked,
                                            onCheckedChange = { checked = !checked
                                            property.setValue(checked)
                                            })

                                    }
                                )

                            }
                        }
                    }


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {


                        Button(

                            onClick = {
                                //add action here
                                viewModel.exerciseBundleMain.add(mutableListOf(movement))
                                openDialog = false
                            },
                            elevation = ButtonDefaults.buttonElevation(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        ) {
                            Text(text = "Add")
                        }
                        //cancel add
                        Button(
                            onClick = { openDialog = false },
                            elevation = ButtonDefaults.buttonElevation(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        ) {
                            Text(text = "Cancel")
                        }
                    }


                }


            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionColumn(
    exerciseList: List<AllExercises>,
    viewModel: DayViewModel
) {

    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { viewModel.closeSelection() })
            {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "backToDayView")
            }
            Text(text = "Exercises", fontSize = MaterialTheme.typography.titleLarge.fontSize)
        }

    }) { contentPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .alpha(0.4f), color = MaterialTheme.colorScheme.onBackground
            )
            exerciseList.forEach {
                it
                SelectionCard(movement = it, viewModel = viewModel, "selectionItem")
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .alpha(0.4f), color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionCard(
    movement: AllExercises,
    viewModel: DayViewModel,
    contentDescription: String,
) {
    var dialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .clickable(true, onClick = {
                    dialog = true

//                    viewModel.exerciseBundleMain.add(mutableStateListOf(movement))
//                    viewModel.closeSelection()
                })
                .fillMaxWidth(1f)
                .height(60.dp)

        ) {
            //Divider(modifier = Modifier.fillMaxWidth(1f).alpha(0.6f), )
            Row(
                modifier = Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(id = movement.iconId),
                    contentDescription = contentDescription,
                    modifier = Modifier
                        .scale(scaleX = 2f, scaleY = 2f)
                        .padding(10.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        movement.name,
                        modifier = Modifier.padding(start = 5.dp),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { /*TODO Go to a details screen*/ }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "Bruh")

                    }
                }
            }

        }
        //Divider(modifier = Modifier.fillMaxWidth(1f))
        if (dialog) {
            GenericAddPopUp(movement = movement) {
                TextField(value = "bruh", onValueChange = {})
            }
        }

    }
}
