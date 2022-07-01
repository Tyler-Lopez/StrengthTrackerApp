package com.company.strengthtracker.presentation.template_day_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.material.icons.filled.CalendarViewWeek
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.company.strengthtracker.ui.theme.Shapes
import com.himanshoe.kalendar.common.KalendarSelector
import com.himanshoe.kalendar.common.KalendarStyle
import com.himanshoe.kalendar.common.theme.KalendarShape
import com.himanshoe.kalendar.ui.Kalendar
import com.himanshoe.kalendar.ui.KalendarType
import java.time.LocalDate

@Composable
fun customCalendar(
    state: Boolean,
    updateDay: (LocalDate) -> Unit
) {

    if (state) {
        Kalendar(
            kalendarType = KalendarType.Oceanic(),
            kalendarStyle = KalendarStyle(
                kalendarBackgroundColor = MaterialTheme.colorScheme.surface,
                kalendarColor = MaterialTheme.colorScheme.surface,
                //kalendarColor = Color.White,
                elevation = 0.dp,
                //kalendarSelector = KalendarSelector.CutCornerSquare(),
                shape = KalendarShape.ButtomCurvedShape,
                kalendarSelector = KalendarSelector.Dot(
                    selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    defaultTextColor = MaterialTheme.colorScheme.onSurface,
                    eventTextColor = Color.White,
                    defaultColor = MaterialTheme.colorScheme.surface,
                    todayColor = MaterialTheme.colorScheme.onSurface,
                    selectedColor = MaterialTheme.colorScheme.inverseOnSurface
                )
            ),

            onCurrentDayClick = { day, event ->
                updateDay(day)
            },
            errorMessage = {
                //Handle the error if any
            })

    } else {
        Kalendar(
            kalendarType = KalendarType.Firey(),
            kalendarStyle = KalendarStyle(

                kalendarBackgroundColor = MaterialTheme.colorScheme.surface,
                kalendarColor = MaterialTheme.colorScheme.surface,
                elevation = 0.dp,
                //kalendarSelector = KalendarSelector.CutCornerSquare(),
                shape = KalendarShape.ButtomCurvedShape,
                kalendarSelector = KalendarSelector.Dot(
                    //selectedColor = MaterialTheme.colorScheme.primaryVariant,
                    //todayColor = spanishGrey,
                    selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    defaultTextColor = MaterialTheme.colorScheme.onSurface,
                    eventTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    defaultColor = MaterialTheme.colorScheme.surface,
                    todayColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedColor = MaterialTheme.colorScheme.inverseOnSurface
                )
            ),

            onCurrentDayClick = { day, event ->
                updateDay(day)
                //handle the date click listener
            },
            errorMessage = {
                //Handle the error if any
            })
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpandCalendar(
    width: Float = 1.0f,
    padding: Dp = 10.dp,
    updateDay: (LocalDate) -> Unit
) {
    var expandedState by remember { mutableStateOf(true) }
    var curDate by remember { mutableStateOf(LocalDate.now()) }

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
        elevation = CardDefaults.cardElevation(0.dp),
        shape = Shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) {

            var animationState by remember { mutableStateOf(true) }
            var animationStateExpanded by remember { mutableStateOf(false) }

            if (expandedState) {

                customCalendar(expandedState, updateDay = {
                    updateDay(it)
                })


            } else {


                customCalendar(expandedState) {
                    updateDay(it)
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier
                        .alpha(.8f)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    if (expandedState) {
                        Icon(
                            imageVector = Icons.Filled.CalendarViewMonth,
                            contentDescription = "switch to month view"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.CalendarViewWeek,
                            contentDescription = "switch to week view"
                        )
                    }
                }
            }


        }
    }
}