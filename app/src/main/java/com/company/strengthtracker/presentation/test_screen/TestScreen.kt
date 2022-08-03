package com.company.strengthtracker.presentation.test_screen

import android.graphics.Paint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.strengthtracker.domain.util.GraphData
import com.company.strengthtracker.presentation.template_day_screen.TopBar
import com.company.strengthtracker.presentation.test_screen.graph_utils.*

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(navController: NavController, viewModel: TestViewModel = hiltViewModel()) {
    val colors = MaterialTheme.colorScheme
    var graphUtil by remember { viewModel.dataList }
    Scaffold(
        topBar = {



        },
        containerColor = MaterialTheme.colorScheme.surface,


        ) {
        val bruh = it.calculateBottomPadding()
        Column(
            modifier =
            Modifier
                .fillMaxSize().padding(it)
                .background(colors.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val graphData by remember {
                mutableStateOf(
                    GraphData(
                        graphUtil,
                        padding = 0f,
                        coordinateFormatter = CoordinateFormatter(),
                    )
                )
            }

            ChartHolder(graphData = graphData, colors = colors)
        }

    }
}

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartHolder(
    graphData: GraphData,
    colors: ColorScheme,
) {
    var borderOffset by remember { mutableStateOf(Offset.Zero) }

    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    // textPaint to construct text objects within the graph
    val density = LocalDensity.current
    val textPaint =
        remember(density) {
            Paint().apply {
                color = android.graphics.Color.WHITE
                textAlign = Paint.Align.LEFT
                textSize = density.run { 12.sp.toPx() }
            }
        }
    textPaint.isAntiAlias = true
    var pan by remember { mutableStateOf(Offset.Zero) }

    Column(
        modifier = Modifier
            .fillMaxSize(1f).clipToBounds()
            .onGloballyPositioned { layoutCoordinates ->
                val rect: androidx.compose.ui.geometry.Rect = layoutCoordinates.boundsInRoot()
            },
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9f)
                .background(colors.surface),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End
        ) {

            Column(
                modifier = Modifier
                    .background(colors.surface)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(0.05f)
            ) {
                ChartYAxis(
                    colors = colors,
                    scale = scale,
                    offset = offset,
                    graphData = graphData,
                )


            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.9f)
                    .background(colors.surface)
                    .clip(RectangleShape)

            ) {
                LineChart(
                    graphData = GraphData(
                        graphDataList = graphData.graphDataList,
                        padding = graphData.padding,
                        coordinateFormatter = graphData.coordinateFormatter
                    ), colors = colors, scale = scale, offset = offset,
                    onScaleChanged = {
                        scale = it
                    },
                    onOffsetChanged = {
                        offset = it
                    },
                    gestureListener = { centroid, panAmount, zoom ->
                        pan = panAmount
                    },
                    textPaint = textPaint,
                )

            }
        }
        Row(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier
                    .background(colors.surface)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(0.05f)
            ) {

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.9f)
                    .background(colors.surface)

            ) {
                ChartXAxis(
                    colors = colors,
                    scale = scale,
                    pan = pan,
                    offset = offset,
                    graphData = graphData
                )
            }


        }
    }
}


/*

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
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var selectedValue by remember { mutableStateOf(-1) }
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

    // box for maintaining 1:1 aspect ratio
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .aspectRatio(1f)
            .fillMaxSize(0.9f)
    ) {
        // Column for centering
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {}) { Text("") }
            Text(
                text = "Dips Progress: 8 Weeks",
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.Monospace,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                modifier = Modifier.padding(10.dp)
            )
            Canvas(
                modifier =
                Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTransformGestures { centroid, pan, zoom, _ ->
                            val prevScale = scale
                            scale *= zoom
                            offset =
                                (offset + centroid / prevScale) - (centroid / scale + pan / prevScale)
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
                    coordinateFormatter.normalizeCoordinates(
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

                */
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
                *//*

                var stepSize = (height / yMax) // scaled measurement of '1' unit on graph
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

                var stepSizeX = (height / xMax) // scaled measurement of '1' unit on graph
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
*/
