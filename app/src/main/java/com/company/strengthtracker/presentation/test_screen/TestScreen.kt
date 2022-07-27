package com.company.strengthtracker.presentation.test_screen

import android.content.ContentValues.TAG
import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.strengthtracker.domain.util.NormalizeLists
import com.company.strengthtracker.presentation.test_screen.graph_utils.CoordinateFormatter
import com.company.strengthtracker.ui.theme.DarkGrey10
import java.util.Collections.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(navController: NavController, viewModel: TestViewModel = hiltViewModel()) {
    val colors = MaterialTheme.colorScheme
/*
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
*/
    var height by remember { mutableStateOf(0f)}
    var width by remember { mutableStateOf(0f)}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .onGloballyPositioned { layoutCoordinates ->
                width = layoutCoordinates.size.width.toFloat()
                height = layoutCoordinates.size.height.toFloat()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(elevation = CardDefaults.cardElevation(3.dp), shape = MaterialTheme.shapes.medium, modifier = Modifier.fillMaxWidth(0.95f)) {

/*
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
*/
            var yMax = Math.max(viewModel.listYInitial.maxOrNull() ?: Float.MIN_VALUE, viewModel.listYCurrent.maxOrNull() ?: Float.MIN_VALUE)
            var yMin = Math.min(viewModel.listYInitial.minOrNull() ?: Float.MIN_VALUE, viewModel.listYCurrent.minOrNull() ?: Float.MIN_VALUE)
            var xMax = Math.max(viewModel.xli.maxOrNull() ?: Float.MIN_VALUE, viewModel.xlc.maxOrNull() ?: Float.MIN_VALUE)
            var xMin = Math.min(viewModel.xli.minOrNull() ?: Float.MIN_VALUE, viewModel.xlc.minOrNull() ?: Float.MIN_VALUE)
            ComparisonGraph(
               height = height,
               width = width,
                xListInitial = viewModel.xli,
                xListCurrent = viewModel.xlc,
                yListInitial = viewModel.listYInitial,
                yListCurrent = viewModel.listYCurrent,
                totalYMaxInit = yMax,
                totalYMinInit = yMin,
                //PLACEHOLDER
                totalXMax = xMax,
                totalXMin = xMin,
                //PLACEHOLDER
                padding = 50f,
                coordinateFormatter = CoordinateFormatter(),
                colors = colors
            )
        }
    }
}

/*x_0 = scaledXDist, x_1 += scaledXDist
 * y = (yMax - y)*(height/yMax)*/
/**/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComparisonGraph(
    height: Float,
    width:Float,
    xListInitial: MutableList<Float>,
    xListCurrent: MutableList<Float>,
    yListInitial: MutableList<Float>,
    yListCurrent: MutableList<Float>,
    totalYMaxInit: Float,
    totalYMinInit: Float,
    totalXMax: Float,
    totalXMin: Float,
    padding: Float,
    coordinateFormatter: CoordinateFormatter,
    colors: ColorScheme,
) {
    var totalYMax = totalYMaxInit + 20f
    var totalYMin = totalYMinInit - 20f
    // pixel density ref for Paint
    val density = LocalDensity.current
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var sCenter = remember {mutableStateOf(Offset(width * 0.5f, (width * 0.5f)))}
    sCenter.value = Offset(width * 0.5f, width * 0.5f)
    var w by remember { mutableStateOf(0f) }
    var h by remember { mutableStateOf(0f) }
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
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Start, modifier = Modifier.aspectRatio(1f)
    ) {
/*
        Column(modifier = Modifier
            .fillMaxWidth(0.1f)
            .fillMaxHeight(1f)
            .background(colors.error)){

        }
*/
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .aspectRatio(1f)
                .fillMaxSize(1f)
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
                    modifier = Modifier
                        .fillMaxSize(1f)
                        .clip(MaterialTheme.shapes.extraSmall)
                        .pointerInput(Unit) {
                            detectTransformGestures(
                                panZoomLock = false,
                                onGesture = {centroid, pan, zoom, rotation ->
                                    val prevScale = scale
                                    val ts = (scale * zoom).coerceIn(0.9f, 4f)
                                    scale = ts


                                    var to =  (offset + centroid / prevScale) - (centroid / scale + pan / prevScale)
                                    var testOffset = Offset(to.x + (0.5f * size.width), to.y + (0.5f * size.height))
//                                    sCenter.value = Offset((sCenter.value.x + offset.x ), (offset.y + sCenter.value.y ))
                                    Log.d("bruh ", "${sCenter.value.x}, ${sCenter.value.y}")

                                    if((testOffset.x + 50.dp.toPx() >= 0f && testOffset.x <= size.width ) && (testOffset.y + 50.dp.toPx()>= 0f && testOffset.y <= size.height ) && scale > 1f) {
                                        offset = to
                                        sCenter.value = Offset((sCenter.value.x ) , (sCenter.value.y)  )
                                    }
                                    else if (scale <= 1f){
                                        offset = Offset.Zero

                                    }
                                    else{
                                        offset = offset
                                        }

//                                    scale = ts

                            }

                            )

/*
                            detectTransformGestures { centroid, pan, zoom, _ ->
                                val prevScale = scale
                                val ts = (scale * zoom).coerceIn(0.9f, 100f)
                                var to =  (offset + centroid / prevScale) - (centroid / scale + pan / prevScale)
                                to = Offset(to.x.coerceIn(-size.width.toFloat() + 50.dp.toPx(), size.width.toFloat() + 50.dp.toPx()), to.y.coerceIn(-size.height.toFloat() - 50.dp.toPx(), size.height.toFloat() + 50.dp.toPx()) )
//                                to = Offset(to.x - (centroid.x / ts + pan.x / prevScale), to.y - (centroid.y / ts + pan.y / prevScale))
                                scale = ts
//                                    offset = (offset + centroid / prevScale) - (centroid / scale + pan / prevScale)
//                                    offset = Offset(offset.x + centroid.x, offset.y + centroid.y)

                                offset = to

                            }
*/
                        }
                        .graphicsLayer {

                                scaleX = scale
                                scaleY = scale
                                translationX = -offset.x * scale
                                translationY = -offset.y * scale

//                            sCenter.value = Offset(sCenter.value.x + (translationX), sCenter.value.y + (translationY) )
                                transformOrigin = TransformOrigin(0f, 0f)

                        }
                ) {
                    val width = size.width
                    val height = size.height
                    //get coordinate list
                    w = width
                    h = height
                    drawIntoCanvas {
                        drawCircle(
                            color = colors.error,
                            radius = 5f,
                            center = sCenter.value
                        )

                        var axisXMin = totalXMin
                        var axisYMin = totalYMin
                        var axisXMax = totalXMax
                        var axisYMax = totalYMax
                        // x-axis
                        drawLine(
                            start = Offset(padding - axisXMin, ((axisYMax) * (height / axisYMax))),
                            end = Offset(width + padding, ((axisYMax - 0) * (height / axisYMax))),
                            color = Color.Black,
                            strokeWidth = 5f
                        )

                        drawLine(
                            start = Offset((padding - axisXMax), 0f),
                            end = Offset(padding - axisXMin, (axisYMax - axisYMin) * (height / (axisYMax - axisYMin))),
                            color = colors.onSurface,
                            strokeWidth = 5f
                        )

                    }
                    /*       var current = coordinateFormatter.getCoordList(
                               listX = xListCurrent,
                               listY = yListCurrent,
                               yMax = totalYMax,
                               totalYMin,
                               xMax = xListCurrent.maxOrNull() ?: Float.MIN_VALUE,
                               xMin = xListCurrent.minOrNull() ?: Float.MIN_VALUE,
                               height = height,
                               width = width,
                               padding = padding
                           )
                           var initial = coordinateFormatter.getCoordList(
                               listX = xListInitial,
                               listY = yListInitial,
                               yMax = totalYMax,
                               totalYMin,
                               xMax = xListInitial.maxOrNull() ?: Float.MIN_VALUE,
                               xMin = xListInitial.minOrNull() ?: Float.MIN_VALUE,
                               height = height,
                               width = width,
                               padding = padding
                           )*/
                    var current = coordinateFormatter.getCoordList(
                        listX = xListCurrent,
                        listY = yListCurrent,
                        yMax = totalYMax,
                        totalYMin,
                        xMax = xListCurrent.maxOrNull() ?: Float.MIN_VALUE,
                        xMin = xListCurrent.minOrNull() ?: Float.MIN_VALUE,
//                    xMax = totalXMax,
//                    xMin = totalXMin,
                        height = height,
                        width = width,
                        padding = padding
                    )
                    var initial = coordinateFormatter.getCoordList(
                        listX = xListInitial,
                        listY = yListInitial,
                        yMax = totalYMax,
                        totalYMin,
                        xMax = xListInitial.maxOrNull() ?: Float.MIN_VALUE,
                        xMin = xListInitial.minOrNull() ?: Float.MIN_VALUE,
//                    xMax = totalXMax,
//                    xMin = totalXMin,
                        height = height,
                        width = width,
                        padding = padding
                    )


                    var stepSize = 0f
                    val increment = height / (totalYMax - totalYMin)
                    val x1 = width / (totalXMax)
                    Log.d(TAG, "width ==> " + width)
                    Log.d(TAG, "xMax ==> " + width)
                    var text = totalYMax
                    for (i in totalYMin.toInt()..(totalYMax.toInt())) {
                        if (i % 10 == 0 && text > totalYMin) {
                            drawContext.canvas.nativeCanvas.drawText(
                                "${text}",
                                (0.5f * (padding - totalXMin)),
                                (stepSize + (0.3f * textPaint.textSize)),
                                textPaint
                            )
                            drawLine(
                                color = colors.onSurface,
                                start = Offset(x = (padding - totalXMin) - 8f, y = stepSize),
                                end = Offset(x = (padding - totalXMin) + 8f, y = stepSize),
                                strokeWidth = 5f
                            )
                            drawLine(
                                color = colors.onSurface,
                                start = Offset((padding - totalXMin) + 8f, stepSize),
                                end = Offset(width + padding, stepSize),
                                strokeWidth = 2f,
                                alpha = 0.6f,
                                pathEffect = PathEffect.dashPathEffect(
                                    floatArrayOf(x1, x1, x1, x1),
                                )
                            )

                        }
                        text -= 1f
                        stepSize += increment
                    }



                    for (i in current.indices) {
                        if ((i + 1) < current.size) {
                            drawLine(
                                color = colors.onSurface,
                                start = current[i],
                                end = current[i + 1],
                                strokeWidth = 10f
                            )
                        }
                    }
                    for (i in current.indices) {
                        drawCircle(color = colors.onSurfaceVariant, radius = 10f, center = current[i])
                    }

                    for (i in initial.indices) {
                        if ((i + 1) < current.size) {
                            drawLine(
                                color = colors.error,
                                start = initial[i],
                                end = initial[i + 1],
                                strokeWidth = 10f
                            )
                        }
                    }
                    for (i in initial.indices) {
                        drawCircle(color = colors.onSurfaceVariant, radius = 10f, center = initial[i])
                    }
                }
            }
        }
        //box for maintaining 1:1 aspect ratio
    }
/*
    Row(modifier = Modifier
        .fillMaxHeight(0.1f)
        .fillMaxWidth(1f)
        .background(colors.onError)){

    }
*/
}

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
            }) { Text("") }
            Text(text = "Dips Progress: 8 Weeks", fontSize = MaterialTheme.typography.titleSmall.fontSize, fontStyle = FontStyle.Normal, fontFamily = FontFamily.Monospace, fontWeight = MaterialTheme.typography.titleLarge.fontWeight, modifier = Modifier.padding(10.dp))
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTransformGestures { centroid, pan, zoom, _ ->
                            val prevScale = scale
                            scale *= zoom
                            offset = (offset + centroid / prevScale) - (centroid / scale + pan / prevScale)
                        }
                    }
                    .graphicsLayer {
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
                        0f,
                        xMax = xMax,
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


/*
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
*/
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
                for (i in coordinateList.indices) {
                    drawLine(
                        color = Color.Black,
                        start = Offset(x = coordinateList[i].x, y = ((yMax) * (height / yMax)) + 10f),
                        end = Offset(x = coordinateList[i].x, y = ((yMax) * (height / yMax)) - 10f),
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
                            strokeWidth = 10f
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

