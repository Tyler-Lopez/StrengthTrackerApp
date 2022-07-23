package com.company.strengthtracker.presentation.progress_screen

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.strengthtracker.presentation.test_screen.graph_utils.CoordinateFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(navController: NavController, viewModel: ProgressViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        SingleLineGraph(
//            listX = listX,
//            listY = listY,
//            yMax = yMax,
//            xMax = xMax,
//            yMin = yMin,
//            xMin = xMin,
//            coordinateFormatter = CoordinateFormatter(),
//            colors = colors,
//            padding = 50f
//        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComparisonGraph(
    xListInitial: List<Float>,
    xListCurrent: List<Float>,
    yListInitial: List<Float>,
    yListCurrent: List<Float>,
    totalYMax: Float,
    totalXMax: Float,
    totalXMin: Float,
    padding: Float,
    coordinateFormatter: CoordinateFormatter,
    colors: ColorScheme,
) {

    // pixel density ref for Paint
    val density = LocalDensity.current

    // textPaint to construct text objects within the graph
    val textPaint =
        remember(density) {
            Paint().apply {
                color = android.graphics.Color.WHITE
                textAlign = Paint.Align.RIGHT
                textSize = density.run { 12.sp.toPx() }
            }
        }
    // setting text anti alias to on
    textPaint.isAntiAlias = true

    //box for maintaining 1:1 aspect ratio
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .aspectRatio(1f)
            .fillMaxSize(0.9f)
    ) {
        //Column for centering
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.surfaceVariant),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize(0.8f)
            ) {
                val width = size.width
                val height = size.height
//TODO move the coordinate list formatting to an alternative class outside of composables
                //get coordinate list
               var current = coordinateFormatter.getCoordList(
                   listX = xListCurrent,
                   listY = yListCurrent,
                   yMax = yListCurrent.maxOrNull() ?: Float.MIN_VALUE,
                   xMax = xListCurrent.maxOrNull() ?: Float.MIN_VALUE,
                   yMin = yListCurrent.minOrNull() ?: Float.MIN_VALUE,
                   xMin = xListCurrent.minOrNull() ?: Float.MIN_VALUE,
                   height = height,
                   width = width,
                   padding = padding
               )
                var initial = coordinateFormatter.getCoordList(
                    listX = xListInitial,
                    listY = yListInitial,
                    yMax = yListInitial.maxOrNull() ?: Float.MIN_VALUE,
                    xMax = xListInitial.maxOrNull() ?: Float.MIN_VALUE,
                    yMin = yListInitial.minOrNull() ?: Float.MIN_VALUE,
                    xMin = xListInitial.minOrNull() ?: Float.MIN_VALUE,
                    height = height,
                    width = width,
                    padding = padding
                )

                // x-axis
                drawLine(
                    start = Offset(padding - totalXMin, ((totalXMax) * (height / totalYMax)).toFloat()),
                    end = Offset(width, ((totalYMax - 0) * (height / totalYMax)).toFloat()),
                    color = Color.Black,
                    strokeWidth = 5f
                )

                // y-axis
                drawLine(
                    start = Offset((padding - totalXMin), ((totalYMax - 0) * (height / totalYMax))),
                    end = Offset(padding - totalXMin, (height / totalYMax)),
                    color = colors.onSurface,
                    strokeWidth = 5f
                )

                var stepSize = (height / totalYMax) //scaled measurement of '1' unit on graph
                var increment = stepSize
                var text = totalYMax
                for (i in 0..totalYMax.toInt()) {
                    if (i % 5 == 0 && text > 0f) {
                        drawContext.canvas.nativeCanvas.drawText(
                            "${text.toInt()}",
                            (0.5f * (padding - totalXMin)),
                            (stepSize + (0.3f * textPaint.textSize)),
                            textPaint
                        )
                        drawLine(
                            color = Color.Black,
                            start = Offset(x = (padding - totalXMin) - 8f, y = stepSize),
                            end = Offset(x = (padding - totalXMin) + 8f, y = stepSize),
                            strokeWidth = 5f
                        )
                    }
                    text -= 1f
                    stepSize += increment
                }



                for (i in current.indices) {
                    if ((i + 1) < 16) {
                        drawLine(
                            color = colors.onSurface,
                            start = current[i],
                            end = current[i + 1],
                            strokeWidth = 5f
                        )
                    }
                }
                for (i in current.indices) {
                    drawCircle(color = colors.onSurfaceVariant, radius = 5f, center = current[i])
                }
                for (i in initial.indices) {
                    if ((i + 1) < 16) {
                        drawLine(
                            color = colors.onSurface,
                            start = initial[i],
                            end = initial[i + 1],
                            strokeWidth = 5f
                        )
                    }
                }
                for (i in initial.indices) {
                    drawCircle(color = colors.onSurfaceVariant, radius = 5f, center = initial[i])
                }
            }
        }
    }
}

/*x_0 = scaledXDist, x_1 += scaledXDist
 * y = (yMax - y)*(height/yMax)*/
/**/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleLineGraph(
    listX: List<Float>,
    listY: List<Float>,
    yMax: Float,
    xMax: Float,
    yMin: Float,
    xMin: Float,
    padding: Float,
    coordinateFormatter: CoordinateFormatter,
    colors: ColorScheme,
) {

    // pixel density ref for Paint
    val density = LocalDensity.current

    // textPaint to construct text objects within the graph
    val textPaint =
        remember(density) {
            Paint().apply {
                color = android.graphics.Color.WHITE
                textAlign = Paint.Align.RIGHT
                textSize = density.run { 12.sp.toPx() }
            }
        }
    // setting text anti alias to on
    textPaint.isAntiAlias = true

    //box for maintaining 1:1 aspect ratio
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .aspectRatio(1f)
            .fillMaxSize(0.9f)
    ) {
        //Column for centering
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.surfaceVariant),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize(0.8f)
            ) {
                val width = size.width
                val height = size.height
                //                val xOffset = ((size.width * 0.5f) - (xMax * 0.5f))
//                val xOffset = ((xMax * 0.5f) - (size.width * 0.5f))

                val coordinateList: MutableList<Offset> =
                    coordinateFormatter.getCoordList(
                        listX = listX,
                        listY = listY,
                        yMax = yMax,
                        xMax = xMax,
                        yMin = yMin,
                        xMin = xMin,
                        height = height,
                        width = width,
                        padding = padding
                    )
                // x-axis
                drawLine(
                    start = Offset(padding - xMin, ((yMax) * (height / yMax)).toFloat()),
                    end = Offset(width, ((yMax - 0) * (height / yMax)).toFloat()),
                    color = Color.Black,
                    strokeWidth = 5f
                )

                // y-axis
                drawLine(
                    start = Offset((padding - xMin), ((yMax - 0) * (height / yMax))),
                    end = Offset(padding - xMin, (height / yMax)),
                    color = colors.onSurface,
                    strokeWidth = 5f
                )

                var stepSize = (height / yMax) //scaled measurement of '1' unit on graph
                var increment = stepSize
                var text = yMax
                for (i in 0..yMax.toInt()) {
                    if (i % 5 == 0 && text > 0f) {
                        drawContext.canvas.nativeCanvas.drawText(
                            "${text.toInt()}",
                            (0.5f * (padding - xMin)),
                            (stepSize + (0.3f * textPaint.textSize)),
                            textPaint
                        )
                        drawLine(
                            color = Color.Black,
                            start = Offset(x = (padding - xMin) - 8f, y = stepSize),
                            end = Offset(x = (padding - xMin) + 8f, y = stepSize),
                            strokeWidth = 5f
                        )
                    }
                    text -= 1f
                    stepSize += increment
                }



                for (i in coordinateList.indices) {
                    if ((i + 1) < 16) {
                        drawLine(
                            color = colors.onSurface,
                            start = coordinateList[i],
                            end = coordinateList[i + 1],
                            strokeWidth = 5f
                        )
                    }
                }
                for (i in coordinateList.indices) {
                    drawCircle(color = colors.onSurfaceVariant, radius = 5f, center = coordinateList[i])
                }
            }
        }
    }
}

