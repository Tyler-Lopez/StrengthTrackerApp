package com.company.strengthtracker.presentation.test_screen

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.strengthtracker.presentation.test_screen.graph_utils.CoordinateFormatter
import com.company.strengthtracker.ui.theme.DarkGrey10

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(navController: NavController, viewModel: TestViewModel = hiltViewModel()) {
    val listX = viewModel.listX
    val listY = viewModel.listY
    val xMax = listX.maxOrNull() ?: 0f
    val yMax = listY.maxOrNull() ?: 0f
    val xMin = listX.minOrNull() ?: 0f
    val yMin = listY.minOrNull() ?: 0f
    val colors = MaterialTheme.colorScheme
    val coordinateList: MutableList<Offset> =
        CoordinateFormatter().getCoordList(
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(elevation = CardDefaults.cardElevation(3.dp), shape = MaterialTheme.shapes.medium, modifier = Modifier.fillMaxWidth(0.95f)) {

            SingleLineGraph(
                listX = listX,
                listY = listY,
                yMax = yMax,
                xMax = xMax,
                yMin = yMin,
                xMin = xMin,
                coordinateFormatter = CoordinateFormatter(),
                colors = colors,
                padding = 50f
            )
        }
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


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ComparisonGraph(
//    xListInitial: List<Float>,
//    xListCurrent: List<Float>,
//    yListInitial: List<Float>,
//    yListCurrent: List<Float>,
//    totalYMax: Float,
//    totalXMax: Float,
//    totalXMin: Float,
//    height: Float,
//    width: Float,
//    padding: Float,
//    coordinateFormatter: CoordinateFormatter,
//    colors: ColorScheme,
//) {
//
//    // pixel density ref for Paint
//    val density = LocalDensity.current
//
//    // textPaint to construct text objects within the graph
//    val textPaint =
//        remember(density) {
//            Paint().apply {
//                color = android.graphics.Color.WHITE
//                textAlign = Paint.Align.RIGHT
//                textSize = density.run { 12.sp.toPx() }
//            }
//        }
//    // setting text anti alias to on
//    textPaint.isAntiAlias = true
//
//    //box for maintaining 1:1 aspect ratio
//    Box(
//        contentAlignment = Alignment.Center, modifier = Modifier
//            .aspectRatio(1f)
//            .fillMaxSize(0.9f)
//    ) {
//        //Column for centering
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(colors.surfaceVariant),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Canvas(
//                modifier = Modifier.fillMaxSize(0.8f)
//            ) {
//                val width = size.width
//                val height = size.height
////TODO move the coordinate list formatting to an alternative class outside of composables
//                val coordinateList: MutableList<Offset> =
//                    coordinateFormatter.getComparisonCoordList(
//                    )
//                // x-axis
//                drawLine(
//                    start = Offset(padding - xMin, ((yMax) * (height / yMax)).toFloat()),
//                    end = Offset(width, ((yMax - 0) * (height / yMax)).toFloat()),
//                    color = Color.Black,
//                    strokeWidth = 5f
//                )
//
//                // y-axis
//                drawLine(
//                    start = Offset((padding - xMin), ((yMax - 0) * (height / yMax))),
//                    end = Offset(padding - xMin, (height / yMax)),
//                    color = colors.onSurface,
//                    strokeWidth = 5f
//                )
//
//                var stepSize = (height / yMax) //scaled measurement of '1' unit on graph
//                var increment = stepSize
//                var text = yMax
//                for (i in 0..yMax.toInt()) {
//                    if (i % 5 == 0 && text > 0f) {
//                        drawContext.canvas.nativeCanvas.drawText(
//                            "${text.toInt()}",
//                            (0.5f * (padding - xMin)),
//                            (stepSize + (0.3f * textPaint.textSize)),
//                            textPaint
//                        )
//                        drawLine(
//                            color = Color.Black,
//                            start = Offset(x = (padding - xMin) - 8f, y = stepSize),
//                            end = Offset(x = (padding - xMin) + 8f, y = stepSize),
//                            strokeWidth = 5f
//                        )
//                    }
//                    text -= 1f
//                    stepSize += increment
//                }
//
//
//
//                for (i in coordinateList.indices) {
//                    if ((i + 1) < 16) {
//                        drawLine(
//                            color = colors.onSurface,
//                            start = coordinateList[i],
//                            end = coordinateList[i + 1],
//                            strokeWidth = 5f
//                        )
//                    }
//                }
//                for (i in coordinateList.indices) {
//                    drawCircle(color = colors.onSurfaceVariant, radius = 5f, center = coordinateList[i])
//                }
//            }
//        }
//    }
//}

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
    var scale by remember {
        mutableStateOf(1f)
    }
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }
    var selectedValue by remember {
        mutableStateOf(-1)
    }
    // pixel density ref for Paint
    val density = LocalDensity.current

    // textPaint to construct text objects within the graph
    val textPaintYIndices =
        remember(density) {
            Paint().apply {
                color = android.graphics.Color.BLACK
                textAlign = Paint.Align.RIGHT
                textSize = density.run { 12.sp.toPx() }
            }
        }
    // setting text anti alias to on
    textPaintYIndices.isAntiAlias = true



    val textPaintHeader =
        remember(density) {
            Paint().apply {
                color = android.graphics.Color.BLACK
                textAlign = Paint.Align.RIGHT
                textSize = density.run { 36.sp.toPx() }
            }
        }
    // setting text anti alias to on
    textPaintYIndices.isAntiAlias = true



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
                .background(colors.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
            }){Text("")}
            Text(text = "Dips Progress: 8 Weeks", fontSize = MaterialTheme.typography.titleSmall.fontSize, fontStyle = FontStyle.Normal , fontFamily = FontFamily.Monospace,fontWeight = MaterialTheme.typography.titleLarge.fontWeight, modifier = Modifier.padding(10.dp))
            Canvas(
                modifier = Modifier.fillMaxSize().pointerInput(Unit) {
                    detectTransformGestures{ centroid, pan, zoom, _ ->
                        val prevScale = scale
                        scale *= zoom
                        offset = (offset + centroid / prevScale) - (centroid / scale + pan / prevScale)
                    }
                }.graphicsLayer {
                    translationX = -offset.x * scale
                    translationY = -offset.y * scale
                    scaleX = scale
                    scaleY = scale
                    transformOrigin = TransformOrigin(0f, 0f)
                }
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
                    start = Offset(padding - xMin, ((yMax) * (height / yMax))),
                    end = Offset(width + 20f, ((yMax - 0) * (height / yMax))),
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


                drawIntoCanvas {
val stroke = Paint()
                    stroke.textAlign = Paint.Align.CENTER
                    stroke.style = Paint.Style.STROKE
                    stroke.strokeJoin = Paint.Join.ROUND;
                    stroke.strokeMiter = 10.0f;
                    stroke.strokeWidth = 12f; // about 12
                    stroke.color = DarkGrey10.toArgb()
                    stroke.typeface = Typeface.create("Arial", Typeface.BOLD)
                }
                var stepSize = (height / yMax) //scaled measurement of '1' unit on graph
                var increment = stepSize
                var text = yMax
                for (i in 0..yMax.toInt()) {
                    if (i % 5 == 0 && text > 0f) {
                        drawContext.canvas.nativeCanvas.drawText(
                            "${text.toInt()}",
                            (0.5f * (padding - xMin)),
                            (stepSize + (0.3f * textPaintYIndices.textSize)),
                            textPaintYIndices
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

                var stepSizeX = (height / xMax) //scaled measurement of '1' unit on graph
                var incrementX = stepSize
                for (i in coordinateList.indices){
                        drawLine(
                            color = Color.Black,
                            start = Offset(x = coordinateList[i].x, y = ((yMax) * (height / yMax)) + 10f),
                            end = Offset(x =coordinateList[i].x , y = ((yMax) * (height / yMax)) - 10f),
                            strokeWidth = 5f
                        )
                    stepSize += increment
                }


                for (i in coordinateList.indices) {
                    if ((i + 1) < 16) {
                        drawLine(
                            color = colors.onSurface,
                            start = coordinateList[i],
                            end = coordinateList[i + 1],
                            strokeWidth = 8f
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

