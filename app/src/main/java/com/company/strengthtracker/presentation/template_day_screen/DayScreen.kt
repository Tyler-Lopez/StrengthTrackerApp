package com.company.strengthtracker.presentation.template_day_screen

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.view.FrameMetrics.ANIMATION_DURATION
import android.widget.Space
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.strengthtracker.R
import com.company.strengthtracker.data.entities.exercise_data.exercise_definitions.*
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Dynamics
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics
import com.company.strengthtracker.ui.theme.*
import com.himanshoe.kalendar.common.KalendarKonfig
import com.himanshoe.kalendar.common.KalendarSelector
import com.himanshoe.kalendar.common.KalendarStyle
import com.himanshoe.kalendar.common.theme.KalendarShape
import com.himanshoe.kalendar.common.theme.KalendarTheme

import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import kotlin.math.roundToInt
import com.himanshoe.kalendar.ui.Kalendar
import com.himanshoe.kalendar.ui.KalendarType
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun DayScreen(
    navController: NavController,
    viewModel: DayViewModel = hiltViewModel()

) {
    var date: LocalDate = LocalDate.now()
    var dateString by remember { mutableStateOf(date.toString()) }
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Select")
            negativeButton("Close")
        }
    ) {
        datepicker {
            date = it
        }
    }
    val list = remember {
        mutableStateListOf<AllExercises>()
    }

    val bruhList = mutableListOf<AllExercises>()
    bruhList.add(Planche())
    bruhList.add(HandstandPushup())
    bruhList.add(PullUps())
    bruhList.add(Squat())
    bruhList.add(FrontLever())

    var expandedPicker by remember { mutableStateOf(false) }
    var exState by remember{mutableStateOf(false)}

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        AnimatedVisibility(visible = exState, enter = fadeIn(), exit = fadeOut()) {
            ExpandCalendar()
        }

        Row(
            modifier = Modifier
                .padding(10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.fillMaxWidth(0.2f)) {
                Image(
                    painter = painterResource(id = R.drawable.gigachad),
                    contentDescription = "Giga Chad"
                )
            }
            IconButton(
                modifier = Modifier
                    .alpha(ContentAlpha.medium)
                    .weight(1f),
                onClick = {
                   exState = !exState
                }
            ) {

                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "switch to month view"
                    )

            }

            Button(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
                    .padding(2.dp),
                onClick = {
                   
                })
            {
                Text(text = "Notes")
            }
            Button(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxWidth()
                    .padding(2.dp),
                onClick = {
                    expandedPicker = true
                })
            {
                Text(text = "+")
            }

        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ){

            ExpandableExerciseCard(movement = Planche())

        }




        DropdownMenu(
            expanded = expandedPicker,
            onDismissRequest = { expandedPicker = false }
        ) {
            bruhList.forEachIndexed { index, item ->
                DropdownMenuItem(onClick = {
                    list.add(bruhList.get(index))

                }) {
                    Text(text = bruhList.get(index).name)
                }
            }
        }
        LazyColumn() {
            items(items = list) { movement ->
                ExpandableExerciseCard(movement = movement)
            }
        }
    }

}
//@Composable
//fun customExpandCalendar(
//    rotationState: Float = 1.0f,
//)
//{
//    var exState by remember { mutableStateOf(true) }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .animateContentSize(
//                animationSpec = tween(
//                    durationMillis = 1000
//                )
//            ),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top
//        ){
//
//        AnimatedVisibility(visible = exState, enter = fadeIn(), exit = fadeOut()) {
//            customCalendar(exState)
//        }
//        AnimatedVisibility(visible = exState, enter = fadeIn(), exit = fadeOut()) {
//            customCalendar(exState)
//        }
//
//        Row() {
//            IconButton(
//                modifier = Modifier
//                    .alpha(ContentAlpha.high)
//                    .weight(1f)
//                    .rotate(rotationState),
//
//                onClick = {
//                    exState = !exState
//
//                }
//            ) {
//                Icon(
//                    imageVector = Icons.Outlined.Menu,
//                    contentDescription = "Drop-down arrow"
//                )
//            }
//        }
//    }
//
//}

@Composable
fun customCalendar(
    state: Boolean
){
    if(state) {
        MaterialTheme(
            colors = Colors(
                primary = Color.White,
                primaryVariant = davysGrey,
                secondary = davysGrey,
                secondaryVariant = davysGrey,
                background = davysGrey,
                surface = davysGrey,
                error = Color.Red,
                onPrimary = brightGrey,
                onSecondary = davysGrey,
                onBackground = davysGrey,
                onSurface = davysGrey,
                onError = davysGrey,
                isLight = true
            )
        ) {
            Kalendar(
                kalendarType = KalendarType.Oceanic(),
                kalendarStyle = KalendarStyle(
                    kalendarBackgroundColor = Color.White,
                    kalendarColor = Color.White,
                    elevation = 0.dp,
                    //kalendarSelector = KalendarSelector.CutCornerSquare(),
                    shape = KalendarShape.ButtomCurvedShape,
                    kalendarSelector = KalendarSelector.Dot(
                        selectedColor = MaterialTheme.colors.primaryVariant,
                        todayColor = spanishGrey,
                        selectedTextColor = Color.Black,
                        defaultTextColor = Color.Black,
                        eventTextColor = Color.White,
                        defaultColor = Color.White
                    )
                ),

                onCurrentDayClick = { day, event ->
                    //handle the date click listener
                },
                errorMessage = {
                    //Handle the error if any
                })

        }
    }
    else {
        MaterialTheme(
            colors = Colors(
                primary = Color.White,
                primaryVariant = davysGrey,
                secondary = davysGrey,
                secondaryVariant = davysGrey,
                background = davysGrey,
                surface = davysGrey,
                error = Color.Red,
                onPrimary = brightGrey,
                onSecondary = davysGrey,
                onBackground = davysGrey,
                onSurface = davysGrey,
                onError = davysGrey,
                isLight = true
            )
        ) {
            Kalendar(
                kalendarType = KalendarType.Firey(),
                kalendarStyle = KalendarStyle(
                    kalendarBackgroundColor = Color.White,
                    kalendarColor = Color.White,
                    elevation = 0.dp,
                    //kalendarSelector = KalendarSelector.CutCornerSquare(),
                    shape = KalendarShape.ButtomCurvedShape,
                    kalendarSelector = KalendarSelector.Dot(
                        selectedColor = MaterialTheme.colors.primaryVariant,
                        todayColor = spanishGrey,
                        selectedTextColor = Color.Black,
                        defaultTextColor = Color.Black,
                        eventTextColor = Color.White,
                        defaultColor = Color.White
                    )
                ),

                onCurrentDayClick = { day, event ->
                    //handle the date click listener
                },
                errorMessage = {
                    //Handle the error if any
                })

        }
    }
}


@ExperimentalMaterialApi
@Composable
fun ExpandCalendar(
    titleFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    width: Float = 1.0f,
    padding: Dp = 10.dp
) {
    var expandedState by remember { mutableStateOf(true) }

    val rotationState by animateFloatAsState(
        targetValue =
        if (expandedState) 180f
        else 0f
    )


    Card(
        modifier = Modifier
            .fillMaxWidth(width)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            .padding(padding)
            .shadow(10.dp),
        shape = Shapes.medium,
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) {

            var animationState by remember {mutableStateOf(true)}
            var animationStateExpanded by remember {mutableStateOf(false)}

            if(expandedState){

                    AnimatedVisibility(visible = animationState, enter = fadeIn(), exit = fadeOut(),) {
                    customCalendar(expandedState)

                }
            }
          else {

                AnimatedVisibility(visible = animationState, enter = fadeIn() + expandVertically(), exit = fadeOut()) {
                    customCalendar(expandedState)
                        // animationState = true
                }
          }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    if(expandedState) {
                        Icon(
                            imageVector = Icons.Filled.CalendarViewMonth,
                            contentDescription = "switch to month view"
                        )
                    }
                    else{
                        Icon(
                            imageVector = Icons.Filled.CalendarViewWeek,
                            contentDescription = "switch to week view"
                        )
                    }
                }
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        animationState = !animationState
                        animationStateExpanded = !animationStateExpanded
                    }
                ) {
                    if(expandedState) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "switch to month view"
                        )
                    }
                    else{
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "switch to week view"
                        )
                    }
                }
            }


        }
    }
}




@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownCustomSet(
    list: MutableList<AllExercises>
) {
    LazyColumn() {
        items(items = list) { movement ->
            DropDownCustomItem(movement = movement)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownCustomItem(
    movement: AllExercises,

    ) {
    Card(
        modifier = Modifier.fillMaxWidth(0.95f),
        onClick = {}
    ) {
        Text(text = movement.name)
    }
}

@ExperimentalMaterialApi
@Composable
fun ExpandableExerciseCard(
    movement: AllExercises,
    titleFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    width: Float = 1.0f,
    padding: Dp = 10.dp
) {
    val title = movement.name
    var expandedState by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue =
        if (expandedState) 180f
        else 0f
    )


    Card(
        modifier = Modifier
            .fillMaxWidth(width)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300
                )
            )
            .padding(padding)
            .shadow(10.dp),
        shape = Shapes.medium,
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f)
                        .fillMaxWidth(),
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis

                )
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-down arrow"
                    )
                }
            }


            var reps by remember { mutableStateOf("") }
            var dWeight by remember { mutableStateOf("") }
            var rir by remember { mutableStateOf("") }
            var notes by remember { mutableStateOf(movement.notes) }

            if (expandedState) {

                if (movement is Statics) {
                    StaticsTextFields(movement = movement)
                } else if (movement is Dynamics) {
                    DynamicsTextFields(movement = movement)
                }
            }
        }
    }
}

@Composable
fun DynamicsTextFields(movement: Dynamics) {
    var reps by remember { mutableStateOf(movement.reps) }
    var dWeight by remember { mutableStateOf(movement.weight) }
    var rir by remember { mutableStateOf(movement.rir) }
    var notes by remember { mutableStateOf(movement.notes) }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),
        value = reps,
        onValueChange = {
            reps = it
        },
        label = { Text("reps / Set") },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = MaterialTheme.shapes.medium
    )
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),
        value = dWeight,
        onValueChange = {
            dWeight = it
        },
        label = { Text("Weight / Set") },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = MaterialTheme.shapes.medium
    )
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),
        value = rir,
        onValueChange = {
            rir = it
        },
        label = { Text("RIR / Set") },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = MaterialTheme.shapes.medium
    )
//    TextField(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(1.dp),
//        value = notes,
//        onValueChange = {
//            notes = it
//        },
//        label = { Text("Notes") },
//        colors = TextFieldDefaults.textFieldColors(
//            focusedIndicatorColor = Color.Transparent,
//            disabledIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//        ),
//        shape = MaterialTheme.shapes.medium
//    )
}


@Composable
fun StaticsTextFields(movement: Statics) {

    var notes by remember { mutableStateOf(movement.notes) }
    var sHoldTime by remember { mutableStateOf(movement.holdTime) }
    var sWeight by remember { mutableStateOf(movement.weight) }
    var sir by remember { mutableStateOf(movement.sir) }
    var progression by remember { mutableStateOf(movement.progression) }
    var list by remember {mutableStateOf(mutableListOf(1))}

    list.add(1)
    list.add(2)
    list.add(3)
    list.add(4)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        TextField(
            modifier = Modifier
                .weight(2f)
                .padding(1.dp),
            value = sHoldTime,
            onValueChange = {
                sHoldTime = it
                movement.holdTime = it //gotta do this to others
            },
            label = { Text("Hold time / Set") },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = MaterialTheme.shapes.medium
        )
        TextField(
            modifier = Modifier
                .weight(2f)
                .padding(1.dp),
            value = sWeight,
            onValueChange = {
                sWeight = it
            },
            label = { Text("Weight / Set") },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = MaterialTheme.shapes.medium
        )

    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    )
    {
        TextField(
            modifier = Modifier
                .weight(1f)
                .padding(1.dp),
            value = sir,
            onValueChange = {
                sir = it
            },
            label = { Text("SIR / Set") },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = MaterialTheme.shapes.medium
        )
        TextField(
            modifier = Modifier
                .weight(1f)
                .padding(1.dp),
            value = progression,
            onValueChange = {
                progression = it
            },
            label = { Text("Progression / Set") },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = MaterialTheme.shapes.medium
        )
    }

//    TextField(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(1.dp)
//            .height(200.dp),
//        value = notes,
//        maxLines = 5,
//        //singleLine = true,
//        onValueChange = {
//            notes = it
//            movement.notes = it
//        },
//        label = { Text("Notes") },
//        colors = TextFieldDefaults.textFieldColors(
//            focusedIndicatorColor = Color.Transparent,
//            disabledIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//        ),
//        shape = MaterialTheme.shapes.medium
//    )

}
