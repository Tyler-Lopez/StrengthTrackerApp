package com.company.strengthtracker.presentation.template_day_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.strengthtracker.data.entities.exercise_data.exercise_definitions.Progressions
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics
import java.time.LocalDate


//Popup that holds buttons for adding a set, as well as input text fields
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaticsAddSetPopUp(
    movement: Statics,
    viewModel: DayViewModel = hiltViewModel(),
    date: LocalDate,
    setsSoFar: Long


) {
    var openDialog by remember { mutableStateOf(true) }



    if (openDialog) {
        Dialog(
            onDismissRequest = { openDialog = false },
            properties = DialogProperties(),

            ) {
            Card(
                modifier = Modifier
                    .size(300.dp, 370.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(10.dp)
//
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if(movement.properties.getValue("Progression"))
                    {
                        DropDownTextField(
                            label = "Progression",
                            movement = movement
                        ) {

                            movement.progression = it
                        }
                    }
                    if(movement.properties.getValue("Hold time"))
                    {
                        TimeIncrementTextField(
                            label = "Time",
                            movement = movement,
                            trailingLabel = "sec.",
                            onDataChanged = {
                                movement.holdTime = it
                            }
                        )
                    }
                    if(movement.properties.getValue("Weight"))
                    {
                        WeightIncrementTextField(
                            movement = movement,
                            label = "Assistance",
                            trailingLabel = "kg."
                        ) {

                            movement.weight = it
                        }
                    }
                    if(movement.properties.getValue("Seconds in reserve"))
                    {
                        TimeIncrementTextField(
                            movement = movement,
                            label = "SiR",
                            trailingLabel = "sec"
                        ) {

                            movement.sir = it
                        }
                    }
                    if(movement.properties.getValue("Reps"))
                    {
                        //TODO implement reps text field
                        TimeIncrementTextField(
                            label = "Reps",
                            movement = movement,
                            trailingLabel = "reps",
                            onDataChanged = {
                                movement.reps = it
                            }
                        )
                    }


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {

                        //add set
                        Button(

                            onClick = {

                                /*PASS DATA TO VIEWMODEL, IMMEDIATELY MAKE NEW DOCUMENT*/
                                viewModel.addSetHelp(
                                    movement = movement
                                )
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

//custom TextField and +/- widgets
@Composable
fun WeightIncrementTextField(
    movement: Statics,
    label: String,
    trailingLabel: String,
    onDataChanged: (String) -> Unit
) {
    var content by remember { mutableStateOf("0") }
    var trailingIconText by remember { mutableStateOf(trailingLabel) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(.8f),

            value = content,
            label = {
                Text(text = label, color = MaterialTheme.colorScheme.onSurface)
            },
            onValueChange = {
                content = it
                onDataChanged(it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onSurface,

                focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,

                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                disabledIndicatorColor = MaterialTheme.colorScheme.onSurface,
                disabledLabelColor = MaterialTheme.colorScheme.onSurface,
                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            ),
            textStyle = TextStyle(
                //color = Color.Black,
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            ),
            shape = MaterialTheme.shapes.medium,
            trailingIcon = {
                Button(
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    onClick = {
                        if (trailingIconText.equals("kg."))
                            trailingIconText = "lb."
                        else
                            trailingIconText = "kg."
                    }) {
                    Text(text = trailingIconText, color = MaterialTheme.colorScheme.onSurface)
                }
            }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(0.5f)
                .padding(top = 4.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(start = 5.dp)
            )
            {
                IconButton(
                    onClick = {
                        if (content.isDigitsOnly()) {
                            content = (content.toInt() + 1).toString()
                            onDataChanged(content)
                        } else
                            content = 0.toString()
                    },
                    modifier = Modifier.size(19.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "increment-seconds")
                }
                IconButton(onClick = {
                    if (content.toInt() > 0) {
                        content = (content.toInt() - 1).toString()
                        onDataChanged(content)
                    } else if (!content.isDigitsOnly()) {
                        content = 0.toString()
                    }

                }, modifier = Modifier.size(21.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Minimize,
                        contentDescription = "decrement-seconds",
                        // modifier = Modifier.scale(scaleX = 1.05f, scaleY = 1.05f),

                    )
                }
            }
        }

    }
}


//same as weight, gotta combine these later
@Composable
fun TimeIncrementTextField(
    movement: Statics,
    label: String,
    trailingLabel: String,
    onDataChanged: (String) -> Unit
) {
    var content by remember { mutableStateOf("0") }
    var trailingIconText by remember { mutableStateOf("sec.") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(.8f),
            value = content,
            label = {
                Text(text = label, modifier = Modifier.alpha(0.9f))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                content = it
                onDataChanged(it)

                // movement.holdTime = it
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onSurface,

                focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,

                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
            ),
            textStyle = TextStyle(
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            ),
            shape = MaterialTheme.shapes.medium,
            trailingIcon = {
                Button(
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    onClick = {

                    }) {
                    Text(text = trailingIconText, color = MaterialTheme.colorScheme.onSurface)
                }
            }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(0.5f)
                .padding(top = 4.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(start = 5.dp)
            )
            {
                IconButton(
                    onClick = {
                        content = (content.toInt() + 1).toString()
                        onDataChanged(content)
                    },
                    modifier = Modifier.size(19.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "increment-seconds")
                }
                IconButton(onClick = {
                    content = (content.toInt() - 1).toString()
                    onDataChanged(content)
                }, modifier = Modifier.size(21.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Minimize,
                        contentDescription = "decrement-seconds",
                        modifier = Modifier.scale(scaleX = 1.05f, scaleY = 1.05f)
                    )
                }
            }
        }

    }
}


//Textfield with a dropdown menu, currently doesnt take a list as input but could add
@Composable
fun DropDownTextField(
    movement: Statics,
    label: String,
    onDataChanged: (String) -> Unit
) {
    var prog by remember { mutableStateOf(" ") }
    var dropdown by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        OutlinedTextField(
            value = prog,
            label = { Text(text = label, modifier = Modifier.alpha(1f)) },
            leadingIcon = {
                IconButton(
                    onClick = { dropdown = true }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "drop-down-arrow"
                    )
                }
            },
            onValueChange = { prog = it },
            readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onSurface,

                focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,

                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
            ),
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            ),
            shape = MaterialTheme.shapes.medium,

            )
        DropdownMenu(expanded = dropdown, onDismissRequest = { dropdown = false }) {
            var progList = Progressions().progressions
            progList.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = { Text(text = progList.get(index)) },
                    onClick = {
                        prog = progList.get(index)
                        onDataChanged(prog)
                        dropdown = false
                    })
            }
        }
    }
}